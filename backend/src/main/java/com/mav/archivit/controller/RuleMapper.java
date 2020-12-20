package com.mav.archivit.controller;

import com.mav.archivit.model.Rule;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = {KeywordMapper.class})
public interface RuleMapper {

  RuleMapper INSTANCE = Mappers.getMapper(RuleMapper.class);

  RuleDto toDto(Rule rule);

  @Mapping(target = "updated", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "created", ignore = true)
  Rule toModel(RuleDto rule);
}
