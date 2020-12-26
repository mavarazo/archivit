package com.mav.archivit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mav.archivit.ArchivitApplication;
import com.mav.archivit.model.KeywordBuilder;
import com.mav.archivit.model.Rule;
import com.mav.archivit.model.RuleBuilder;
import com.mav.archivit.service.KeywordService;
import com.mav.archivit.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = ArchivitApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("integrationtest")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class KeywordControllerIntegTest {

  public static final String LOREM_IPSUM = "Lorem Ipsum";
  public static final String ZOMBIE_IPSUM = "Zombie Ipsum";
  @Autowired private MockMvc mvc;

  @Autowired private KeywordService auditService;
  @Autowired private RuleService ruleService;

  @Autowired private ObjectMapper objectMapper;
  private Rule rule;

  @BeforeAll
  void setup() {
    rule =
        ruleService.save(
            RuleBuilder.aRule()
                .withName(LOREM_IPSUM)
                .withTargetPath(LOREM_IPSUM)
                .withKeywords(
                    Collections.singletonList(
                        KeywordBuilder.aKeyword().withName(LOREM_IPSUM).build()))
                .build());
  }

  @Test
  void testSave() throws Exception {
    // Arrange
    ImmutableKeywordDto keywordDto = ImmutableKeywordDto.builder().name(ZOMBIE_IPSUM).build();

    // Act && Assert
    mvc.perform(
            post("/api/rule/" + rule.getId() + "/keyword/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(keywordDto)))
        .andExpect(status().isCreated())
        .andExpect(
            header().string("Location", containsString("api/rule/" + rule.getId() + "/keyword")))
        .andExpect(jsonPath("$.name", is(ZOMBIE_IPSUM)));
  }

  @Test
  void testUpdate() throws Exception {
    // Arrange
    ImmutableKeywordDto keywordDto = ImmutableKeywordDto.builder().name(ZOMBIE_IPSUM).build();

    // Act && Assert
    mvc.perform(
            put("/api/rule/" + rule.getId() + "/keyword/" + rule.getKeywords().get(0).getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(keywordDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(ZOMBIE_IPSUM)));
  }
}
