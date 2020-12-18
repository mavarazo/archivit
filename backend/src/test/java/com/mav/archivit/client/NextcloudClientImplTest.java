package com.mav.archivit.client;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import org.assertj.core.api.Assertions;
import org.mockito.ArgumentCaptor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class NextcloudClientImplTest {

  private NextcloudClientImpl sut;
  private Sardine sardine;

  @BeforeEach
  void initSut() {
    sut = spy(new NextcloudClientImpl());

    sardine = mock(Sardine.class);
    doReturn(sardine).when(sut).getSardine();
  }

  @Test
  void listPdfFromInputPath() throws NextcloudClientException, IOException {
    // Arrange
    DavResource pdfDavResource = mock(DavResource.class);
    doReturn("application/pdf").when(pdfDavResource).getContentType();

    DavResource htmlDavResource = mock(DavResource.class);
    doReturn("application/html").when(htmlDavResource).getContentType();

    doReturn(Arrays.asList(pdfDavResource, htmlDavResource)).when(sardine).list(any());

    // Act
    List<DavResource> result = sut.listPdfFromInputPath();

    // Assert
    assertThat(result).hasSize(1);
  }

  @Test
  void testGet() throws NextcloudClientException, IOException {
    // Arrange
    doReturn(mock(InputStream.class)).when(sardine).get(any());

    // Act
    InputStream result = sut.get("fancy/path/to/a file.pdf");

    // Assert
    ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
    verify(sardine).get(stringArgumentCaptor.capture());
    assertThat(stringArgumentCaptor.getValue().indexOf(" ")).isNotPositive();
  }
}
