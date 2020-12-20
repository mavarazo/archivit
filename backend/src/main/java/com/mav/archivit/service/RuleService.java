package com.mav.archivit.service;

import com.mav.archivit.model.Rule;

import java.util.List;
import java.util.Optional;

public interface RuleService {

  List<Rule> findAll();

  Optional<Rule> findById(Long id);

  Rule save(Rule rule);
}
