package com.mav.archivit.api.dashboard;

import org.immutables.value.Value;

import java.util.List;
import java.util.Map;

@Value.Immutable
public interface Summary {

  Long getFiles();

  Long getUntaggedFiles();

  Long getTags();

  List<String> getExtensions();

  Map<Integer, String> getTopTags();
}
