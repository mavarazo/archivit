package com.mav.archivit.task;

import com.github.sardine.DavResource;
import com.mav.archivit.client.NextcloudClient;
import com.mav.archivit.client.NextcloudClientException;
import com.mav.archivit.model.Audit;
import com.mav.archivit.model.Rule;
import com.mav.archivit.service.AuditService;
import com.mav.archivit.service.RuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
      new PdfHandler(pdf.getPath()) {
        @Override
        Optional<Audit> getAuditByFilePath(String path) {
          return auditService.findByFilePath(path);
        }

        @Override
        void save(Audit audit) {
          auditService.save(audit);
        }

        @Override
        InputStream getPdfContent(String path) {
          InputStream result = null;
          try {
            result = nextcloudClient.get(path);
          } catch (NextcloudClientException e) {
            LOGGER.error(e.getMessage());
          }
          return result;
        }

        @Override
        List<Rule> getRules() {
          return rules;
        }
      }.process();
    }
  }
}
