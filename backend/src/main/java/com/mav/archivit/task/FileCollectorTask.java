package com.mav.archivit.task;

import com.github.sardine.DavResource;
import com.mav.archivit.client.NextcloudClient;
import com.mav.archivit.client.NextcloudClientException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Component
public class FileCollectorTask {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileCollectorTask.class);
  private final NextcloudClient nextcloudClient;

  @Autowired
  public FileCollectorTask(NextcloudClient nextcloudClient) {
    this.nextcloudClient = nextcloudClient;
  }

  @Scheduled(initialDelay = 1000, fixedDelay = Long.MAX_VALUE)
  public void scan() {
    LOGGER.info("Start scanner");
    try {
      List<DavResource> pdfs = nextcloudClient.listPdfFromInputPath();
      for (DavResource pdf : pdfs) {
        InputStream inputStream = nextcloudClient.get(pdf.getPath());
        getPdfContent(inputStream).ifPresent(LOGGER::info);
      }
    } catch (NextcloudClientException e) {
      LOGGER.error(e.getMessage());
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
}
