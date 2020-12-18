package com.mav.archivit.task;

import com.github.sardine.DavResource;
import com.mav.archivit.client.NextcloudClient;
import com.mav.archivit.client.NextcloudClientException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

class FileCollectorTaskTest {

  private FileCollectorTask sut;
  private NextcloudClient nextcloudClient;

  @BeforeEach
  void initSut() {
    nextcloudClient = mock(NextcloudClient.class);
    sut = spy(new FileCollectorTask(nextcloudClient));
  }

  @Test
  void testScan() throws NextcloudClientException, FileNotFoundException {
    // Arrange
    DavResource pdfDavResource = mock(DavResource.class);
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
