package com.mav.archivit.task;

import com.github.sardine.DavResource;
import com.mav.archivit.client.NextcloudClient;
import com.mav.archivit.client.NextcloudClientException;
import com.mav.archivit.model.Audit;
import com.mav.archivit.model.AuditBuilder;
import com.mav.archivit.model.KeywordBuilder;
import com.mav.archivit.model.RuleBuilder;
import com.mav.archivit.model.StatusEnum;
import com.mav.archivit.service.AuditService;
import com.mav.archivit.service.RuleService;
import org.mockito.ArgumentCaptor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileCollectorTaskTest {

  public static final String PDF_TO_PROCESS = "pdf-to-process.pdf";
  public static final String PDF_ALREADY_PROCESSED_PDF = "pdf-already-processed.pdf";
  public static final String PDF_TO_RETRY = "pdf-to-retry.pdf";

  @Test
  void testScan() throws NextcloudClientException {
    // Arrange
    TestcaseBuilder tcBuilder = new TestcaseBuilder().addDavResource(PDF_TO_PROCESS).build();

    // Act
    tcBuilder.sut.scan();

    // Assert
    ArgumentCaptor<Audit> auditArgumentCaptor = ArgumentCaptor.forClass(Audit.class);
    verify(tcBuilder.auditService).save(auditArgumentCaptor.capture());

    assertThat(auditArgumentCaptor.getValue().getFilePath()).isEqualTo(PDF_TO_PROCESS);
    assertThat(auditArgumentCaptor.getValue().getStatus()).isEqualTo(StatusEnum.DONE);
  }

  @Test
  void testScan_pdf_already_processed_status_is_failure() throws NextcloudClientException {
    // Arrange
    TestcaseBuilder tcBuilder =
        new TestcaseBuilder()
            .addDavResource(PDF_ALREADY_PROCESSED_PDF)
            .addAudit(
                AuditBuilder.anAudit()
                    .withFilePath(PDF_ALREADY_PROCESSED_PDF)
                    .withStatus(StatusEnum.FAILURE)
                    .build())
            .build();

    // Act
    tcBuilder.sut.scan();

    // Assert
    verify(tcBuilder.auditService, never()).save(any());
  }

  @Test
  void testScan_pdf_already_processed_status_is_retry() throws NextcloudClientException {
    // Arrange
    TestcaseBuilder tcBuilder =
        new TestcaseBuilder()
            .addDavResource(PDF_TO_RETRY)
            .addAudit(
                AuditBuilder.anAudit()
                    .withFilePath(PDF_TO_RETRY)
                    .withStatus(StatusEnum.RETRY)
                    .build())
            .build();

    // Act
    tcBuilder.sut.scan();

    // Assert
    ArgumentCaptor<Audit> auditArgumentCaptor = ArgumentCaptor.forClass(Audit.class);
    verify(tcBuilder.auditService).save(auditArgumentCaptor.capture());

    assertThat(auditArgumentCaptor.getValue().getFilePath()).isEqualTo(PDF_TO_RETRY);
    assertThat(auditArgumentCaptor.getValue().getStatus()).isEqualTo(StatusEnum.DONE);
  }

  private class TestcaseBuilder {

    FileCollectorTask sut;
    private final AuditService auditService;
    private final NextcloudClient nextcloudClient;
    private final RuleService ruleService;

    private final List<DavResource> davResources = new ArrayList<>();

    public TestcaseBuilder() {
      auditService = mock(AuditService.class);
      nextcloudClient = mock(NextcloudClient.class);
      ruleService = mock(RuleService.class);

      sut = spy(new FileCollectorTask(nextcloudClient, auditService, ruleService));
    }

    public TestcaseBuilder addDavResource(String path) {
      DavResource davResource = mock(DavResource.class);
      doReturn(path).when(davResource).getPath();
      davResources.add(davResource);
      return this;
    }

    public TestcaseBuilder addAudit(Audit audit) {
      doReturn(Optional.ofNullable(audit)).when(auditService).findByFilePath(any());
      return this;
    }

    public TestcaseBuilder build() throws NextcloudClientException {
      ClassLoader classLoader = getClass().getClassLoader();

      doReturn(davResources).when(nextcloudClient).listPdfFromInputPath();

      doAnswer(
              invocation -> {
                File file = null;

                String argument = invocation.getArgument(0, String.class);
                if (PDF_TO_PROCESS.equals(argument)) {
                  file = new File(classLoader.getResource(PDF_TO_PROCESS).getFile());
                } else if (PDF_TO_RETRY.equals(argument)) {
                  file = new File(classLoader.getResource(PDF_TO_RETRY).getFile());
                }
                return nonNull(file) ? new FileInputStream(file) : null;
              })
          .when(nextcloudClient)
          .get(any());

      doReturn(
              Arrays.asList(
                  RuleBuilder.aRule()
                      .withName("Lorem Ipsum")
                      .withTargetPath("Lorem")
                      .withKeywords(
                          Arrays.asList(
                              KeywordBuilder.aKeyword().withName("Lorem").build(),
                              KeywordBuilder.aKeyword().withName("Ipsum").build()))
                      .build(),
                  RuleBuilder.aRule()
                      .withName("Zombie Ipsum")
                      .withTargetPath("Zombie")
                      .withKeywords(
                          Arrays.asList(
                              KeywordBuilder.aKeyword().withName("Zombie").build(),
                              KeywordBuilder.aKeyword().withName("Ipsum").build()))
                      .build()))
          .when(ruleService)
          .findAll();

      return this;
    }
  }
}
