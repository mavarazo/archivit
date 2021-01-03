package com.mav.archivit.controller;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.immutables.value.Value;

import java.math.BigDecimal;

@Value.Immutable
@JsonSerialize(as = ImmutableMatchDto.class)
@JsonDeserialize(as = ImmutableMatchDto.class)
public interface MatchDto {

  RuleDto getRule();

  BigDecimal getScore();
}
