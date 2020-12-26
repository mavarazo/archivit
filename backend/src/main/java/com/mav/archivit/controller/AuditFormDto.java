package com.mav.archivit.controller;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mav.archivit.model.StatusEnum;

import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutableAuditFormDto.class)
@JsonDeserialize(as = ImmutableAuditFormDto.class)
public interface AuditFormDto {

  String getFilePath();

  StatusEnum getStatus();

  List<MatchDto> getMatches();
}
