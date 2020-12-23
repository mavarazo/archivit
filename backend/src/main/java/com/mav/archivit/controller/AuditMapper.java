package com.mav.archivit.controller;

import com.mav.archivit.model.Audit;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = {MatchMapper.class})
public interface AuditMapper {

  AuditMapper INSTANCE = Mappers.getMapper(AuditMapper.class);

  AuditDto toDto(Audit audit);

  @Mapping(target = "created", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "updated", ignore = true)
  @Mapping(target = "matches", ignore = true)
  void toModel(AuditDto auditDto, @MappingTarget Audit audit);
}
