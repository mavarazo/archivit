package com.mav.archivit.task;

import com.mav.archivit.model.Audit;
import com.mav.archivit.model.Match;
import com.mav.archivit.model.Rule;
import com.mav.archivit.model.StatusEnum;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static java.util.Map.Entry;

public abstract class PdfHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(PdfHandler.class);

  private final Audit audit;

  @Autowired
  protected PdfHandler(String path) {
    audit =
        getAuditByFilePath(path)
            .orElseGet(
                () -> {
                  Audit a = new Audit();
                  a.setFilePath(path);
                  return a;
                });
  }

  abstract Optional<Audit> getAuditByFilePath(String path);

  abstract void save(Audit audit);

  public void process() {
    if (StatusEnum.OPEN != audit.getStatus() && StatusEnum.RETRY != audit.getStatus()) {
      LOGGER.info(
          "PDF '{}' already processed and status is '{}'.", audit.getFilePath(), audit.getStatus());
      return;
    }
    processPdf();
    save(audit);
  }

  abstract InputStream getPdfContent(String path);

  void processPdf() {
    InputStream inputStream = getPdfContent(audit.getFilePath());
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
                audit.setStatus(StatusEnum.DONE);
              });
    } else {
      audit.setStatus(StatusEnum.FAILURE);
      LOGGER.info("No matches for '{}'", audit.getFilePath());
    }
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

  abstract List<Rule> getRules();

  private Map<Rule, BigDecimal> processPdfContent(String pdfContent) {
    Map<Rule, BigDecimal> results =
        getRules().stream()
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
