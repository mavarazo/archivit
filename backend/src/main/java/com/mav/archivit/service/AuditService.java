package com.mav.archivit.service;

import com.mav.archivit.model.Audit;

import java.util.List;
import java.util.Optional;

public interface AuditService {

  List<Audit> findAll();

  Optional<Audit> findById(Long id);
}
