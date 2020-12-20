package com.mav.archivit.controller;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableKeywordDto.class)
@JsonDeserialize(as = ImmutableKeywordDto.class)
public interface KeywordDto {

  String getName();
}
