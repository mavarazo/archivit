package com.mav.archivit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mav.archivit.ArchivitApplication;
import com.mav.archivit.model.AuditBuilder;
import com.mav.archivit.model.MatchBuilder;
import com.mav.archivit.model.RuleBuilder;
import com.mav.archivit.model.StatusEnum;
import com.mav.archivit.service.AuditService;
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

import java.math.BigDecimal;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
public class AuditControllerIntegTest {

  public static final String LOREM_IPSUM = "Lorem Ipsum";
  public static final String ZOMBIE_IPSUM = "Zombie Ipsum";
  @Autowired private MockMvc mvc;

  @Autowired private AuditService auditService;
  @Autowired private RuleService ruleService;

  @Autowired private ObjectMapper objectMapper;

  @BeforeAll
  void setup() {
    auditService.save(
        AuditBuilder.anAudit()
            .withFilePath(LOREM_IPSUM)
            .withStatus(StatusEnum.DONE)
            .withMatches(
                Collections.singletonList(
                    MatchBuilder.aMatch()
                        .withRule(
                            ruleService.save(RuleBuilder.aRule().withName(LOREM_IPSUM).withTargetPath(LOREM_IPSUM).build()))
                        .withScore(BigDecimal.valueOf(100))
                        .build()))
            .build());
  }

  @Test
  void testUpdate() throws Exception {
    // Arrange
    ImmutableAuditFormDto auditDto =
          ImmutableAuditFormDto.builder().filePath(LOREM_IPSUM).status(StatusEnum.RETRY).build();

    // Act && Assert
    mvc.perform(
            put("/api/audit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(auditDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is(StatusEnum.RETRY.toString())));
  }
}