package com.mav.archivit.task;

import com.mav.archivit.model.Match;
import com.mav.archivit.model.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class Matcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(Matcher.class);

  private final List<Rule> rules;

  public Matcher(List<Rule> rules) {
    this.rules = rules;
  }

  public List<Match> process(Pdf pdf) {
    Optional<String> pdfContent = pdf.getContent();
    if (!pdfContent.isPresent()) {
      return Collections.emptyList();
    }

    Map<Rule, BigDecimal> results =
        rules.stream()
            .collect(
                Collectors.toMap(
                    rule -> rule,
                    rule -> {
                      AtomicReference<BigDecimal> score = new AtomicReference<>(BigDecimal.ZERO);
                      BigDecimal step = BigDecimal.valueOf(100 / rule.getKeywords().size());

                      rule.getKeywords().stream()
                          .filter(
                              keyword -> pdfContent.get().contains(keyword.getName().toLowerCase()))
                          .forEach(keyword -> score.getAndAccumulate(step, BigDecimal::add));

                      return score.get();
                    }));

    results.forEach(
        (key, value) -> LOGGER.info("Rule '{}' scored with '{}'", key.getName(), value));
    return mapToMatches(results);
  }

  private List<Match> mapToMatches(Map<Rule, BigDecimal> results) {
    return results.entrySet().stream()
        .sorted(Map.Entry.comparingByValue())
        .filter(e -> e.getValue().compareTo(BigDecimal.valueOf(75)) > -1)
        .map(
            e -> {
              Match match = new Match();
              match.setRule(e.getKey());
              match.setScore(e.getValue());
              return match;
            })
        .peek(m -> LOGGER.info("Match '{}' scored with '{}'", m.getRule().getName(), m.getScore()))
        .collect(Collectors.toList());
  }
}
