package com.mav.archivit.repository;

import com.mav.archivit.model.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
  Optional<Audit> findByFilePath(String path);
}
