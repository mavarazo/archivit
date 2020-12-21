package com.mav.archivit.model;

import java.time.LocalDateTime;
import java.util.Set;

public final class KeywordBuilder {
   Set<Rule> rules;
   private Long id;
   private LocalDateTime created;
   private LocalDateTime updated;
   private String name;

   private KeywordBuilder() {
   }

   public static KeywordBuilder aKeyword() {
      return new KeywordBuilder();
   }

   public KeywordBuilder withId(Long id) {
      this.id = id;
      return this;
   }

   public KeywordBuilder withCreated(LocalDateTime created) {
      this.created = created;
      return this;
   }

   public KeywordBuilder withUpdated(LocalDateTime updated) {
      this.updated = updated;
      return this;
   }

   public KeywordBuilder withName(String name) {
      this.name = name;
      return this;
   }

   public KeywordBuilder withRules(Set<Rule> rules) {
      this.rules = rules;
      return this;
   }

   public Keyword build() {
      Keyword keyword = new Keyword();
      keyword.setId(id);
      keyword.setCreated(created);
      keyword.setUpdated(updated);
      keyword.setName(name);
      keyword.setRules(rules);
      return keyword;
   }
}
