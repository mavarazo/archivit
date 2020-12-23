package com.mav.archivit.controller;

import com.mav.archivit.model.Keyword;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface KeywordMapper {

  KeywordMapper INSTANCE = Mappers.getMapper(KeywordMapper.class);

  KeywordDto toDto(Keyword rule);

  @Mapping(target = "created", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "rule", ignore = true)
  @Mapping(target = "updated", ignore = true)
  Keyword toModel(KeywordDto keyword);

  List<Keyword> toModel(List<KeywordDto> keyword);

  @Mapping(target = "created", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "rule", ignore = true)
  @Mapping(target = "updated", ignore = true)
  void toModel(KeywordDto keywordDto, @MappingTarget Keyword keyword);
}
