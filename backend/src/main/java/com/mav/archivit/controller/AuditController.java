package com.mav.archivit.controller;

import com.mav.archivit.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/audit")
public class AuditController {

  private final AuditService auditService;

  @Autowired
  public AuditController(AuditService auditService) {
    this.auditService = auditService;
  }

  @GetMapping("/")
  @ResponseBody
  public ResponseEntity<List<AuditDto>> index() {
    return ResponseEntity.ok(
        auditService.findAll().stream()
            .map(AuditMapper.INSTANCE::toDto)
            .collect(Collectors.toList()));
  }
}
