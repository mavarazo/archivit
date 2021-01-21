package com.mav.archivit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mav.archivit.ArchivitApplication;
import com.mav.archivit.model.KeywordBuilder;
import com.mav.archivit.model.Rule;
import com.mav.archivit.model.RuleBuilder;
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

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = ArchivitApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class RuleControllerIntegTest {

  public static final String LOREM_IPSUM = "Lorem Ipsum";
  public static final String ZOMBIE_IPSUM = "Zombie Ipsum";
  @Autowired private MockMvc mvc;

  @Autowired private RuleService service;

  @Autowired private ObjectMapper objectMapper;

  @BeforeAll
  void setup() {
    Rule rule = RuleBuilder.aRule().withName(LOREM_IPSUM).withTargetPath(LOREM_IPSUM).build();
    rule.getKeywords().add(KeywordBuilder.aKeyword().withName(LOREM_IPSUM).withRule(rule).build());
    service.save(rule);
  }

  @Test
  void testIndex() throws Exception {
    // Act && Assert
    mvc.perform(get("/api/rule/").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].name", is("Lorem Ipsum")))
        .andExpect(jsonPath("$[0].targetPath", is(LOREM_IPSUM)))
        .andExpect(jsonPath("$[0].keywords[0].name", is(LOREM_IPSUM)));
  }

  @Test
  void testGet_not_found() throws Exception {
    // Act && Assert
    mvc.perform(get("/api/rule/-1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void testGet_ok() throws Exception {
    // Act && Assert
    mvc.perform(get("/api/rule/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.name", is(LOREM_IPSUM)))
        .andExpect(jsonPath("$.targetPath", is(LOREM_IPSUM)))
    // .andExpect(jsonPath("$.keywords[0].name", is(LOREM_IPSUM)))
    ;
  }

  @Test
  void testSave() throws Exception {
    // Arrange
    ImmutableRuleFormDto ruleDto =
        ImmutableRuleFormDto.builder()
            .name(LOREM_IPSUM)
            .targetPath(LOREM_IPSUM)
            .addKeywords(ImmutableKeywordFormDto.builder().name(LOREM_IPSUM).build())
            .build();

    // Act && Assert
    mvc.perform(
            post("/api/rule/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(ruleDto)))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/api/rule/2"))
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.name", is(LOREM_IPSUM)))
    // .andExpect(jsonPath("$.keywords[0].name", is(LOREM_IPSUM)))
    ;
  }

  @Test
  void testUpdate() throws Exception {
    // Arrange
    ImmutableRuleFormDto ruleDto =
        ImmutableRuleFormDto.builder().name(ZOMBIE_IPSUM).targetPath(LOREM_IPSUM).build();

    // Act && Assert
    mvc.perform(
            put("/api/rule/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(ruleDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(ZOMBIE_IPSUM)))
        .andExpect(jsonPath("$.targetPath", is(LOREM_IPSUM)))
    // .andExpect(jsonPath("$.keywords[0].name", is(LOREM_IPSUM)))
    ;
  }
}
