package com.mav.archivit.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public final class RuleBuilder {
   Set<Keyword> keywords = new HashSet<>();
   private Long id;
   private LocalDateTime created;
   private LocalDateTime updated;
   private String name;
   private String targetPath;

   private RuleBuilder() {
   }

   public static RuleBuilder aRule() {
      return new RuleBuilder();
   }

   public RuleBuilder withId(Long id) {
      this.id = id;
      return this;
   }

   public RuleBuilder withCreated(LocalDateTime created) {
      this.created = created;
      return this;
   }

   public RuleBuilder withUpdated(LocalDateTime updated) {
      this.updated = updated;
      return this;
   }

   public RuleBuilder withName(String name) {
      this.name = name;
      return this;
   }

   public RuleBuilder withTargetPath(String targetPath) {
      this.targetPath = targetPath;
      return this;
   }

   public RuleBuilder withKeywords(Set<Keyword> keywords) {
      this.keywords = keywords;
      return this;
   }

   public Rule build() {
      Rule rule = new Rule();
      rule.setId(id);
      rule.setCreated(created);
      rule.setUpdated(updated);
      rule.setName(name);
      rule.setTargetPath(targetPath);
      rule.setKeywords(keywords);
      return rule;
   }
}
