package com.mav.archivit.controller;

import com.mav.archivit.model.Keyword;
import com.mav.archivit.service.KeywordService;
import com.mav.archivit.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("api/rule/{rule_id}/keyword")
public class KeywordController {

  private final KeywordService keywordService;
  private final RuleService ruleService;

  @Autowired
  public KeywordController(KeywordService keywordService, RuleService ruleService) {
    this.keywordService = keywordService;
    this.ruleService = ruleService;
  }

  @PostMapping("/")
  @ResponseBody
  public ResponseEntity<KeywordDto> save(
      @Validated @NonNull @RequestBody KeywordFormDto keywordDto,
      @PathVariable("rule_id") Long ruleId) {

    return ruleService
        .findById(ruleId)
        .map(
            rule -> {
              Keyword keyword = keywordService.save(KeywordMapper.INSTANCE.toModel(keywordDto));
              keyword.setRule(rule);

              return ResponseEntity.created(
                      ServletUriComponentsBuilder.fromCurrentRequest()
                          .path("/{id}")
                          .buildAndExpand(keyword.getId())
                          .toUri())
                  .body(KeywordMapper.INSTANCE.toDto(keyword));
            })
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  @ResponseBody
  public ResponseEntity<KeywordDto> update(
      @Validated @NonNull @RequestBody KeywordFormDto keywordDto, @PathVariable("id") Long id) {

    return keywordService
        .findById(id)
        .map(
            keyword -> {
              KeywordMapper.INSTANCE.toModel(keywordDto, keyword);
              keyword = keywordService.save(keyword);
              return ResponseEntity.ok(KeywordMapper.INSTANCE.toDto(keyword));
            })
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}