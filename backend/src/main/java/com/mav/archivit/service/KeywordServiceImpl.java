package com.mav.archivit.service;

import com.mav.archivit.model.Keyword;
import com.mav.archivit.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class KeywordServiceImpl implements KeywordService {

  private final KeywordRepository keywordRepository;

  @Autowired
  public KeywordServiceImpl(KeywordRepository keywordRepository) {
    this.keywordRepository = keywordRepository;
  }

  @Override
  public Optional<Keyword> findById(Long id) {
    return keywordRepository.findById(id);
  }

  @Override
  @Transactional
  public Keyword save(Keyword keyword) {
    return keywordRepository.save(keyword);
  }
}
