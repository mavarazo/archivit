package com.mav.archivit.controller;

import com.mav.archivit.model.Rule;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = {KeywordMapper.class})
public interface RuleMapper {

  RuleMapper INSTANCE = Mappers.getMapper(RuleMapper.class);

  RuleDto toDto(Rule rule);

  @Mapping(target = "created", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "updated", ignore = true)
  Rule toModel(RuleDto rule);

  @Mapping(target = "created", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "updated", ignore = true)
  @Mapping(target = "keywords", ignore = true)
  void toModel(RuleDto ruleDto, @MappingTarget Rule rule);
}
