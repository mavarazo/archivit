package com.mav.archivit.service;

import com.mav.archivit.model.Keyword;
import com.mav.archivit.model.Rule;
import com.mav.archivit.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RuleServiceImpl implements RuleService {

  private final RuleRepository ruleRepository;

  @Autowired
  public RuleServiceImpl(RuleRepository ruleRepository) {
    this.ruleRepository = ruleRepository;
  }

  @Override
  public List<Rule> findAll() {
    List<Rule> rules = ruleRepository.findAll();
    return rules.stream()
        .map(
            rule -> {
              rule.getKeywords()
                  .sort(
                      Comparator.comparing(Keyword::getId)
                          .thenComparing(Keyword::getCreated)
                          .thenComparing(Keyword::getUpdated));
              return rule;
            })
        .sorted(
            Comparator.comparing(Rule::getId)
                .thenComparing(Rule::getCreated)
                .thenComparing(Rule::getUpdated))
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Rule> findById(Long id) {
    return ruleRepository.findById(id);
  }

  @Override
  @Transactional
  public Rule save(Rule rule) {
    return ruleRepository.save(rule);
  }
}
