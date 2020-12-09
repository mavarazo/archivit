package com.mav.archivit.api.tag;

import org.immutables.value.Value;

@Value.Immutable
public interface TagDto {

  Long getId();

  String getName();
}
