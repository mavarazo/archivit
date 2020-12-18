package com.mav.archivit.controller;

import org.immutables.value.Value;

@Value.Immutable
public interface AuditDto {

  String getFilePath();
}
