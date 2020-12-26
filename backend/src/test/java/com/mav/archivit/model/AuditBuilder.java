package com.mav.archivit.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class AuditBuilder {
  private Long id;
  private LocalDateTime created;
  private LocalDateTime updated;
  private String filePath;
  private StatusEnum status = StatusEnum.OPEN;
  private List<Match> matches = new ArrayList<>();

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

  public AuditBuilder withStatus(StatusEnum status) {
    this.status = status;
    return this;
  }

  public AuditBuilder withMatches(List<Match> matches) {
    this.matches = matches;
    return this;
  }

  public Audit build() {
    Audit audit = new Audit();
    audit.setId(id);
    audit.setCreated(created);
    audit.setUpdated(updated);
    audit.setFilePath(filePath);
    audit.setStatus(status);
    audit.setMatches(matches);
    return audit;
  }
}
