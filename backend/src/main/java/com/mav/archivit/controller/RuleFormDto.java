package com.mav.archivit.controller;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutableRuleFormDto.class)
@JsonDeserialize(as = ImmutableRuleFormDto.class)
public interface RuleFormDto {

  String getName();

  String getTargetPath();

  List<KeywordFormDto> getKeywords();
}
