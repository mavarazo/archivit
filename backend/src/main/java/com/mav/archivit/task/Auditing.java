package com.mav.archivit.task;

import com.mav.archivit.model.Audit;
import com.mav.archivit.model.Match;
import com.mav.archivit.model.Rule;
import com.mav.archivit.model.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

public class Auditing {

  private static final Logger LOGGER = LoggerFactory.getLogger(Auditing.class);

  private final Audit audit;
  private final List<Rule> rules;
  private final Pdf pdf;

  public String getFilePath() {
    return audit.getFilePath();
  }

  public void setStatus(StatusEnum status) {
    audit.setStatus(status);
  }

  public Audit get() {
    return audit;
  }

  public Auditing(Audit audit, List<Rule> rules, Pdf pdf) {
    this.audit = audit;
    this.rules = rules;
    this.pdf = pdf;
  }

  public boolean isProcessable() {
    if (StatusEnum.OPEN != audit.getStatus() && StatusEnum.RETRY != audit.getStatus()) {
      LOGGER.info(
          "PDF '{}' already processed and status is '{}'.", audit.getFilePath(), audit.getStatus());
      return false;
    }
    return true;
  }

  public Set<Match> process() {
    if (!isProcessable()) {
      return audit.getMatches();
    }

    audit.setMatches(new Matcher(rules).process(pdf));
    return audit.getMatches();
  }
}
