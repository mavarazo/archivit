package com.mav.archivit;

import com.mav.archivit.model.Audit;
import com.mav.archivit.model.Keyword;
import com.mav.archivit.model.Match;
import com.mav.archivit.model.Rule;
import com.mav.archivit.model.StatusEnum;
import com.mav.archivit.service.AuditService;
import com.mav.archivit.service.RuleService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;

@SpringBootApplication
public class ArchivitApplication {

  public static void main(String[] args) {
    SpringApplication.run(ArchivitApplication.class, args);
  }

  @Bean
  @Profile("dev")
  ApplicationRunner init(RuleService ruleService, AuditService auditService) {
    return args -> {
      Rule rule = new Rule();
      rule.setName("Lorem Ipsum");
      rule.setTargetPath("Lorem Ipsum");
      Keyword keyword = new Keyword();
      keyword.setName("Lorem");
      keyword.setRule(rule);
      rule.getKeywords().add(keyword);
      ruleService.save(rule);

      Audit audit = new Audit();
      audit.setFilePath("Lorem Ipsum.pdf");
      audit.setStatus(StatusEnum.DONE);
      Match match = new Match();
      match.setScore(BigDecimal.valueOf(100));
      match.setRule(rule);
      match.setAudit(audit);
      audit.getMatches().add(match);
      auditService.save(audit);
    };
  }
}
