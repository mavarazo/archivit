package com.mav.archivit.controller;

import com.mav.archivit.model.StatusEnum;

import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
public interface AuditDto {

  String getFilePath();

  StatusEnum getStatus();

  List<MatchDto> getMatches();
}
