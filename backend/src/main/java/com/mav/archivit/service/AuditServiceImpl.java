package com.mav.archivit.service;

import com.mav.archivit.model.Audit;
import com.mav.archivit.model.Match;
import com.mav.archivit.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuditServiceImpl implements AuditService {

  private final AuditRepository auditRepository;

  @Autowired
  public AuditServiceImpl(AuditRepository auditRepository) {
    this.auditRepository = auditRepository;
  }

  @Override
  @Transactional
  public List<Audit> findAll() {
    return auditRepository
        .findAll(Sort.by("id").and(Sort.by("created").and(Sort.by("updated"))))
        .stream()
        .map(
            audit -> {
              audit.getMatches().sort(Comparator.comparing(Match::getId));
              return audit;
            })
        .collect(Collectors.toList());
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