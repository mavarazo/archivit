package com.mav.archivit.service;

import com.mav.archivit.model.Audit;
import com.mav.archivit.repository.AuditRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

class AuditServiceImplTest {

  private AuditServiceImpl sut;
  private AuditRepository auditRepository;

  @BeforeEach
  void initSut() {
    auditRepository = mock(AuditRepository.class);
    sut = spy(new AuditServiceImpl(auditRepository));
  }

  @Test
  void testFindAll() {
    // Arrange
    doReturn(Arrays.asList(new Audit(), new Audit())).when(auditRepository).findAll();

    // Act
    List<Audit> result = sut.findAll();

    // Assert
    assertThat(result).hasSize(2);
  }
}
