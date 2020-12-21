package com.mav.archivit.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public final class AuditBuilder {
  private Long id;
  private LocalDateTime created;
  private LocalDateTime updated;
  private String filePath;
  private boolean isProcessed = false;
  private Set<Match> matches = new HashSet<>();

  private AuditBuilder() {}

  public static AuditBuilder anAudit() {
    return new AuditBuilder();
  }

  public AuditBuilder withId(Long id) {
    this.id = id;
    return this;
  }

  public AuditBuilder withCreated(LocalDateTime created) {
    this.created = created;
    return this;
  }

  public AuditBuilder withUpdated(LocalDateTime updated) {
    this.updated = updated;
    return this;
  }

  public AuditBuilder withFilePath(String filePath) {
    this.filePath = filePath;
    return this;
  }

  public AuditBuilder withProcessed(boolean isProcessed) {
    this.isProcessed = isProcessed;
    return this;
  }

  public AuditBuilder withMatches(Set<Match> matches) {
    this.matches = matches;
    return this;
  }

  public Audit build() {
    Audit audit = new Audit();
    audit.setId(id);
    audit.setCreated(created);
    audit.setUpdated(updated);
    audit.setFilePath(filePath);
    audit.setMatches(matches);
    return audit;
  }
}
