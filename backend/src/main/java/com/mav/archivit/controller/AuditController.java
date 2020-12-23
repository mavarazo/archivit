package com.mav.archivit.controller;

import com.mav.archivit.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @GetMapping("/{id}")
  @ResponseBody
  public ResponseEntity<AuditDto> get(@PathVariable("id") Long id) {
    return auditService
        .findById(id)
        .map(audit -> ResponseEntity.ok(AuditMapper.INSTANCE.toDto(audit)))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  @ResponseBody
  public ResponseEntity<AuditDto> update(
      @Validated @NonNull @RequestBody AuditDto auditDto, @PathVariable("id") Long id) {
    return auditService
        .findById(id)
        .map(
            audit -> {
              AuditMapper.INSTANCE.toModel(auditDto, audit);
              return ResponseEntity.ok(AuditMapper.INSTANCE.toDto(auditService.save(audit)));
            })
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
