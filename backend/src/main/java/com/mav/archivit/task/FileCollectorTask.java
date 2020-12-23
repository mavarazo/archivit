package com.mav.archivit.task;

import com.github.sardine.DavResource;
import com.mav.archivit.client.NextcloudClient;
import com.mav.archivit.client.NextcloudClientException;
import com.mav.archivit.model.Audit;
import com.mav.archivit.model.Match;
import com.mav.archivit.model.Rule;
import com.mav.archivit.model.StatusEnum;
import com.mav.archivit.service.AuditService;
import com.mav.archivit.service.RuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class FileCollectorTask {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileCollectorTask.class);

  private final NextcloudClient nextcloudClient;
  private final AuditService auditService;
  private final RuleService ruleService;
  private List<Rule> rules;

  @Autowired
  public FileCollectorTask(
      NextcloudClient nextcloudClient, AuditService auditService, RuleService ruleService) {
    this.nextcloudClient = nextcloudClient;
    this.auditService = auditService;
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
      Audit audit =
          auditService
              .findByFilePath(pdf.getPath())
              .orElseGet(
                  () -> {
                    Audit a = new Audit();
                    a.setFilePath(pdf.getPath());
                    return a;
                  });

      Auditing auditing = new Auditing(audit, rules, new Pdf(nextcloudClient, pdf));
      if (!auditing.isProcessable()) {
        continue;
      }

      Set<Match> matches = auditing.process();

      if (matches.size() == 1) {
        matches.stream()
            .findFirst()
            .ifPresent(
                match -> {
                  LOGGER.info(
                      "Move '{}' to '{}'", auditing.getFilePath(), match.getRule().getTargetPath());
                  auditing.setStatus(StatusEnum.DONE);
                });
      } else {
        auditing.setStatus(StatusEnum.FAILURE);
        LOGGER.info("No matches for '{}'", auditing.getFilePath());
      }

      auditService.save(auditing.get());
    }
  }
}
