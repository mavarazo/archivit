package com.mav.archivit.controller;

import com.mav.archivit.model.Audit;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AuditMapper {

  AuditMapper INSTANCE = Mappers.getMapper(AuditMapper.class);

  AuditDto toDto(Audit audit);
}
