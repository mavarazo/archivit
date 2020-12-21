package com.mav.archivit.controller;

import org.immutables.value.Value;

import java.math.BigDecimal;

@Value.Immutable
public interface MatchDto {

  BigDecimal getScore();
}
