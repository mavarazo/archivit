package com.mav.archivit.controller;

import com.mav.archivit.model.Rule;
import com.mav.archivit.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/rule")
public class RuleController {

  private final RuleService ruleService;

  @Autowired
  public RuleController(RuleService ruleService) {
    this.ruleService = ruleService;
  }

  @GetMapping("/")
  @ResponseBody
  public ResponseEntity<List<RuleDto>> index() {
    return ResponseEntity.ok(
        ruleService.findAll().stream()
            .map(RuleMapper.INSTANCE::toDto)
            .collect(Collectors.toList()));
  }

  @GetMapping("/{id}")
  @ResponseBody
  public ResponseEntity<RuleDto> get(@PathVariable("id") Long id) {
    return ruleService
        .findById(id)
        .map(rule -> ResponseEntity.ok(RuleMapper.INSTANCE.toDto(rule)))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/")
  @ResponseBody
  public ResponseEntity<RuleDto> save(@RequestBody RuleDto rule) {
    Rule result = ruleService.save(RuleMapper.INSTANCE.toModel(rule));

    return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri())
        .body(RuleMapper.INSTANCE.toDto(result));
  }
}
