package com.mav.archivit.service;

import com.mav.archivit.model.Rule;
import com.mav.archivit.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RuleServiceImpl implements RuleService {

  private final RuleRepository ruleRepository;

  @Autowired
  public RuleServiceImpl(RuleRepository ruleRepository) {
    this.ruleRepository = ruleRepository;
  }

  @Override
  public List<Rule> findAll() {
    return new ArrayList<>(ruleRepository.findAll());
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
