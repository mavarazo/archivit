package com.mav.archivit.controller;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutableRuleDto.class)
@JsonDeserialize(as = ImmutableRuleDto.class)
public interface RuleDto {

  String getName();

  String getTargetPath();

  List<KeywordDto> getKeywords();
}
