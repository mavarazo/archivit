package com.mav.archivit.service;

import com.mav.archivit.model.Audit;
import com.mav.archivit.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

  @Override
  public Optional<Audit> findById(Long id) {
    return auditRepository.findById(id);
  }

  @Override
  public Optional<Audit> findByFilePath(String path) {
    return auditRepository.findByFilePath(path);
  }

  @Override
  @Transactional
  public Audit save(Audit audit) {
    return auditRepository.save(audit);
  }
}