package com.mav.archivit.task;

import com.github.sardine.DavResource;
import com.mav.archivit.client.NextcloudClient;
import com.mav.archivit.client.NextcloudClientException;
import com.mav.archivit.model.KeywordBuilder;
import com.mav.archivit.model.Rule;
import com.mav.archivit.model.RuleBuilder;
import com.mav.archivit.service.AuditService;
import com.mav.archivit.service.RuleService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileCollectorTaskTest {

  private FileCollectorTask sut;
  private AuditService auditService;
  private NextcloudClient nextcloudClient;
  private RuleService ruleService;

  @BeforeAll
  void initSut() {
    auditService = mock(AuditService.class);
    nextcloudClient = mock(NextcloudClient.class);
    ruleService = mock(RuleService.class);
    doReturn(createRules()).when(ruleService).findAll();
    sut = spy(new FileCollectorTask(auditService, nextcloudClient, ruleService));
  }

  private static List<Rule> createRules() {
    return Arrays.asList(
        RuleBuilder.aRule()
            .withName("Lorem Ipsum")
            .withTargetPath("Lorem")
            .withKeywords(
                new HashSet<>(
                    Arrays.asList(
                        KeywordBuilder.aKeyword().withName("Lorem").build(),
                        KeywordBuilder.aKeyword().withName("Ipsum").build())))
            .build(),
        RuleBuilder.aRule()
            .withName("Zombie Ipsum")
            .withTargetPath("Zombie")
            .withKeywords(
                new HashSet<>(
                    Arrays.asList(
                        KeywordBuilder.aKeyword().withName("Zombie").build(),
                        KeywordBuilder.aKeyword().withName("Ipsum").build())))
            .build());
  }

  @Test
  void testScan() throws NextcloudClientException, FileNotFoundException {
    // Arrange
    DavResource pdfDavResource = mock(DavResource.class);
    doReturn("Temp").when(pdfDavResource).getPath();
    doReturn(Collections.singletonList(pdfDavResource))
        .when(nextcloudClient)
        .listPdfFromInputPath();

    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("lorem-ipsum.pdf").getFile());
    doReturn(new FileInputStream(file)).when(nextcloudClient).get(any());

    // Act
    sut.scan();

    // Assert
  }
}
