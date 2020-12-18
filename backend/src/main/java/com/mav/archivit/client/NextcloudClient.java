package com.mav.archivit.client;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NextcloudClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(NextcloudClient.class);

  @Value("${nextcloud.host}")
  private String host;

  @Value("${nextcloud.webdav-path}")
  private String webdav_path;

  @Value("${nextcloud.input-path}")
  private String input_path;

  @Value("${nextcloud.output-path}")
  private String output_path;

  @Value("${nextcloud.username}")
  private String username;

  @Value("${nextcloud.password}")
  private String password;

  private Sardine getSardine() {
    return SardineFactory.begin(username, password);
  }

  public List<DavResource> listPdfFromInputPath() throws NextcloudClientException {
    return list(input_path).stream()
        .filter(resource -> "application/pdf".equals(resource.getContentType()))
        .collect(Collectors.toList());
  }

  public InputStream get(String path) throws NextcloudClientException {
    try {
      String url = host + path.replace(" ", "%20");
      LOGGER.info("Get: {}", url);
      return getSardine().get(url);
    } catch (IOException e) {
      throw new NextcloudClientException(e.getMessage(), e);
    }
  }

  private List<DavResource> list(String directory) throws NextcloudClientException {
    try {
      return getSardine().list(host + webdav_path + directory);
    } catch (IOException e) {
      throw new NextcloudClientException(e.getMessage(), e);
    }
  }
}
