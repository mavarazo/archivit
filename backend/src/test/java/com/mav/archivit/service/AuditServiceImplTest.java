package com.mav.archivit.service;

import com.mav.archivit.model.Audit;
import com.mav.archivit.model.AuditBuilder;
import com.mav.archivit.model.MatchBuilder;
import com.mav.archivit.model.StatusEnum;
import com.mav.archivit.repository.AuditRepository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@TestInstance(Lifecycle.PER_CLASS)
class AuditServiceImplTest {

  private AuditServiceImpl sut;
  private AuditRepository auditRepository;

  @BeforeAll
  void setup() {
    Audit audit =
        AuditBuilder.anAudit()
            .withId(1L)
            .withCreated(LocalDateTime.now())
            .withUpdated(LocalDateTime.now())
            .withFilePath("fancy/path/to/a file.pdf")
            .withStatus(StatusEnum.DONE)
            .withMatches(
                new HashSet<>(
                    Collections.singletonList(
                        MatchBuilder.aMatch().withScore(BigDecimal.valueOf(100)).build())))
            .build();

    auditRepository = mock(AuditRepository.class);
    doReturn(Collections.singletonList(audit)).when(auditRepository).findAll();
    doReturn(Optional.of(audit)).when(auditRepository).findById(eq(1L));

    sut = spy(new AuditServiceImpl(auditRepository));
  }

  @Test
  void testFindAll() {
    // Act
    List<Audit> result = sut.findAll();

    // Assert
    assertThat(result).hasSize(1);
  }

  @Test
  void testFindById() {
    // Act
    Optional<Audit> result = sut.findById(1L);

    // Assert
    assertThat(result).isPresent();
  }

  @Test
  void testFindById_not_found() {
    // Act
    Optional<Audit> result = sut.findById(-1L);

    // Assert
    assertThat(result).isNotPresent();
  }
}
