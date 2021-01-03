package com.mav.archivit.controller;

import com.mav.archivit.model.Match;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = {RuleMapper.class})
public interface MatchMapper {

  MatchMapper INSTANCE = Mappers.getMapper(MatchMapper.class);

  MatchDto toDto(Match match);
}
