package com.mav.archivit.api.user;

import org.immutables.value.Value;

import java.util.List;
import java.util.Map;

@Value.Immutable
public interface UserDto {

  Long getId();

  String getName();

  @Value.Default
  default Long getTotalFiles() {
    return 0L;
  }

  @Value.Default
  default Long getTotalUntaggedFiles() {
    return 0L;
  }

  @Value.Default
  default Long getTotalStarredFiles() {
    return 0L;
  }

  @Value.Default
  default Long getTotalTags() {
    return 0L;
  }

  List<String> getExtensions();

  Map<Integer, String> getTopTags();
}
