package com.mav.archivit.service;

import com.mav.archivit.model.Audit;

import java.util.List;

public interface AuditService {

  List<Audit> findAll();
}
