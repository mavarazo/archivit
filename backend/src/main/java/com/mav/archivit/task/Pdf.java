package com.mav.archivit.task;

import com.github.sardine.DavResource;
import com.mav.archivit.client.NextcloudClient;
import com.mav.archivit.client.NextcloudClientException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class Pdf {

  private static final Logger LOGGER = LoggerFactory.getLogger(Pdf.class);

  private final NextcloudClient nextcloudClient;
  private final DavResource davResource;

  public Pdf(NextcloudClient nextcloudClient, DavResource davResource) {
    this.nextcloudClient = nextcloudClient;
    this.davResource = davResource;
  }

  public String getPath() {
    return davResource.getPath();
  }

  public Optional<String> getContent() {
    try (PDDocument document = PDDocument.load(getPdfContentAsStream())) {
      if (!document.isEncrypted()) {
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        return Optional.of(pdfTextStripper.getText(document).trim());
      }
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
    }
    return Optional.empty();
  }

  private InputStream getPdfContentAsStream() {
    InputStream result = null;
    try {
      result = nextcloudClient.get(davResource.getPath());
    } catch (NextcloudClientException e) {
      LOGGER.error(e.getMessage());
    }
    return result;
  }
}
