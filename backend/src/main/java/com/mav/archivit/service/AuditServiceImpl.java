package com.mav.archivit.service;

import com.mav.archivit.model.Audit;
import com.mav.archivit.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuditServiceImpl implements AuditService {

  private final AuditRepository auditRepository;

  @Autowired
  public AuditServiceImpl(AuditRepository auditRepository) {
    this.auditRepository = auditRepository;
  }

  @Override
  public List<Audit> findAll() {
    return new ArrayList<>(auditRepository.findAll());
  }
}
