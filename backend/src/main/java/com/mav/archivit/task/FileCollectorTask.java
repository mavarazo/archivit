package com.mav.archivit.task;

import com.github.sardine.DavResource;
import com.mav.archivit.client.NextcloudClient;
import com.mav.archivit.client.NextcloudClientException;
import com.mav.archivit.model.Audit;
import com.mav.archivit.model.Match;
import com.mav.archivit.model.Rule;
import com.mav.archivit.service.AuditService;
import com.mav.archivit.service.RuleService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class FileCollectorTask {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileCollectorTask.class);
  private final AuditService auditService;
  private final NextcloudClient nextcloudClient;
  private final RuleService ruleService;

  private List<Rule> rules = new ArrayList<>();

  @Autowired
  public FileCollectorTask(
      AuditService auditService, NextcloudClient nextcloudClient, RuleService ruleService) {
    this.auditService = auditService;
    this.nextcloudClient = nextcloudClient;
    this.ruleService = ruleService;
  }

  @Scheduled(initialDelay = 1000, fixedDelay = Long.MAX_VALUE)
  public void scan() {
    LOGGER.info("Start scanner");
    rules = ruleService.findAll();
    scanForPdfInNextcloud();
  }

  private void scanForPdfInNextcloud() {
    try {
      List<DavResource> pdfs = nextcloudClient.listPdfFromInputPath();
      LOGGER.info("Found '{}' PDF in Nextcloud", pdfs.size());
      processPdfs(pdfs);
    } catch (NextcloudClientException e) {
      LOGGER.error(e.getMessage());
    }
  }

  private void processPdfs(List<DavResource> pdfs) throws NextcloudClientException {
    for (DavResource pdf : pdfs) {
      processPdf(pdf);
    }
  }

  private void processPdf(DavResource pdf) throws NextcloudClientException {
    Audit audit = new Audit();
    audit.setFilePath(pdf.getPath());

    InputStream inputStream = nextcloudClient.get(pdf.getPath());
    Map<Rule, BigDecimal> results =
        getPdfContent(inputStream)
            .map(String::toLowerCase)
            .map(this::processPdfContent)
            .orElseGet(Collections::emptyMap);

    audit.setMatches(mapToMatches(results));
    if (audit.getMatches().size() == 1) {
      audit.getMatches().stream()
          .findFirst()
          .ifPresent(
              match -> {
                LOGGER.info(
                    "Move '{}' to '{}'", audit.getFilePath(), match.getRule().getTargetPath());
                audit.setProcessed(Boolean.TRUE);
              });
    } else {
      LOGGER.info("No matches for '{}'", audit.getFilePath());
    }
    auditService.save(audit);
  }

  private Optional<String> getPdfContent(InputStream inputStream) {
    try (PDDocument document = PDDocument.load(inputStream)) {
      if (!document.isEncrypted()) {
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        return Optional.of(pdfTextStripper.getText(document).trim());
      }
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
    }
    return Optional.empty();
  }

  private Map<Rule, BigDecimal> processPdfContent(String pdfContent) {
    Map<Rule, BigDecimal> results =
        rules.stream()
            .collect(
                Collectors.toMap(
                    rule -> rule,
                    rule -> {
                      AtomicReference<BigDecimal> score = new AtomicReference<>(BigDecimal.ZERO);
                      BigDecimal step = BigDecimal.valueOf(100 / rule.getKeywords().size());

                      rule.getKeywords().stream()
                          .filter(keyword -> pdfContent.contains(keyword.getName().toLowerCase()))
                          .forEach(keyword -> score.getAndAccumulate(step, BigDecimal::add));

                      return score.get();
                    }));

    results.forEach(
        (key, value) -> LOGGER.info("Rule '{}' scored with '{}'", key.getName(), value));
    return results;
  }

  private Set<Match> mapToMatches(Map<Rule, BigDecimal> results) {
    return results.entrySet().stream()
        .sorted(Entry.comparingByValue())
        .filter(e -> e.getValue().compareTo(BigDecimal.valueOf(75)) > -1)
        .map(
            e -> {
              Match match = new Match();
              match.setRule(e.getKey());
              match.setScore(e.getValue());
              return match;
            })
        .peek(m -> LOGGER.info("Match '{}' scored with '{}'", m.getRule().getName(), m.getScore()))
        .collect(Collectors.toSet());
  }
}
