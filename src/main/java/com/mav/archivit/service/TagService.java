package com.mav.archivit.service;

import com.mav.archivit.model.Tag;

import java.util.List;

public interface TagService {

  List<Tag> findAllByUserId(Long userId);

  List<Tag> searchByUserIdAndName(Long userId, String name);
}
