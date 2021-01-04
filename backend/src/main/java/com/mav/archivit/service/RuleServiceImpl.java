package com.mav.archivit.service;

import com.mav.archivit.model.Keyword;
import com.mav.archivit.model.Rule;
import com.mav.archivit.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
  @Transactional
  public List<Rule> findAll() {
    return ruleRepository
        .findAll(Sort.by("id").and(Sort.by("created").and(Sort.by("updated"))))
        .stream()
        .map(
            rule -> {
              rule.getKeywords().sort(Comparator.comparing(Keyword::getId));
              return rule;
            })
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
