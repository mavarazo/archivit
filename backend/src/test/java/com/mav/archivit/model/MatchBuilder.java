package com.mav.archivit.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class MatchBuilder {
   private Long id;
   private LocalDateTime created;
   private LocalDateTime updated;
   private Audit audit;
   private Rule rule;
   private BigDecimal score = BigDecimal.ZERO;

   private MatchBuilder() {
   }

   public static MatchBuilder aMatch() {
      return new MatchBuilder();
   }

   public MatchBuilder withId(Long id) {
      this.id = id;
      return this;
   }

   public MatchBuilder withCreated(LocalDateTime created) {
      this.created = created;
      return this;
   }

   public MatchBuilder withUpdated(LocalDateTime updated) {
      this.updated = updated;
      return this;
   }

   public MatchBuilder withAudit(Audit audit) {
      this.audit = audit;
      return this;
   }

   public MatchBuilder withRule(Rule rule) {
      this.rule = rule;
      return this;
   }

   public MatchBuilder withScore(BigDecimal score) {
      this.score = score;
      return this;
   }

   public Match build() {
      Match match = new Match();
      match.setId(id);
      match.setCreated(created);
      match.setUpdated(updated);
      match.setAudit(audit);
      match.setRule(rule);
      match.setScore(score);
      return match;
   }
}
