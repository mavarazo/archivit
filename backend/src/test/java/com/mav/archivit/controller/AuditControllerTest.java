package com.mav.archivit.controller;

import com.mav.archivit.model.Audit;
import com.mav.archivit.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(
    value = AuditController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
class AuditControllerTest {

  public static final String FILE_PATH = "fancy/path/to/a file.pdf";
  @Autowired private MockMvc mvc;

  @MockBean private AuditService service;

  @Test
  void testIndex() throws Exception {
    // Arrange
    Audit audit = new Audit();
    audit.setId(1L);
    audit.setFilePath("fancy/path/to/a file.pdf");

    doReturn(Collections.singletonList(audit)).when(service).findAll();

    // Act && Assert
    mvc.perform(get("/api/audit/").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].filePath", is(FILE_PATH)));
  }

  @Test
  void testGet_not_found() throws Exception {
    // Arrange
    doReturn(Optional.empty()).when(service).findById(any());

    // Act && Assert
    mvc.perform(get("/api/audit/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void testGet_ok() throws Exception {
    // Arrange
    Audit audit = new Audit();
    audit.setId(1L);
    audit.setFilePath("fancy/path/to/a file.pdf");

    doReturn(Optional.of(audit)).when(service).findById(any());

    // Act && Assert
    mvc.perform(get("/api/audit/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.filePath", is(FILE_PATH)));
  }
}