package com.mav.archivit.service;

import com.mav.archivit.model.Keyword;

import java.util.Optional;

public interface KeywordService {

  Optional<Keyword> findById(Long id);

  Keyword save(Keyword keyword);
}
