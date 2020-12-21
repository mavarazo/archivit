package com.mav.archivit.controller;

import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
public interface AuditDto {

  String getFilePath();

  Boolean getProcessed();

  List<MatchDto> getMatches();
}
