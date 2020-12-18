package com.mav.archivit.client;

import com.github.sardine.DavResource;

import java.io.InputStream;
import java.util.List;

public interface NextcloudClient {

  List<DavResource> listPdfFromInputPath() throws NextcloudClientException;

  InputStream get(String path) throws NextcloudClientException;
}
