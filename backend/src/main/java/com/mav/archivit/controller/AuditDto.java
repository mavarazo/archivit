package com.mav.archivit.controller;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mav.archivit.model.StatusEnum;

import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutableAuditDto.class)
@JsonDeserialize(as = ImmutableAuditDto.class)
public interface AuditDto {

  String getFilePath();

  StatusEnum getStatus();

  List<MatchDto> getMatches();
}
