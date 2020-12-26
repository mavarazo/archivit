package com.mav.archivit.controller;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableKeywordFormDto.class)
@JsonDeserialize(as = ImmutableKeywordFormDto.class)
public interface KeywordFormDto {

  String getName();
}
