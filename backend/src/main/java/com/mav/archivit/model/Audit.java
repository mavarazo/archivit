package com.mav.archivit.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "audits")
@EntityListeners(AuditingEntityListener.class)
public class Audit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreatedDate private LocalDateTime created;

  @LastModifiedDate private LocalDateTime updated;

  private String filePath;

  private boolean isProcessed = false;

  @OneToMany(mappedBy = "audit")
  private Set<Match> matches = new HashSet<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  public LocalDateTime getUpdated() {
    return updated;
  }

  public void setUpdated(LocalDateTime updated) {
    this.updated = updated;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public boolean getProcessed() {
    return isProcessed;
  }

  public void setProcessed(boolean processed) {
    isProcessed = processed;
  }

  public Set<Match> getMatches() {
    return matches;
  }

  public void setMatches(Set<Match> matches) {
    this.matches = matches;
  }
}
