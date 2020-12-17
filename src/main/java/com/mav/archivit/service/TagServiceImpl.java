package com.mav.archivit.service;

import com.mav.archivit.model.Tag;
import com.mav.archivit.model.User;
import com.mav.archivit.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Comparator.comparing;

@Service
public class TagServiceImpl implements TagService {

  private final TagRepository tagRepository;
  private final UserService userService;

  @Autowired
  public TagServiceImpl(TagRepository tagRepository, UserService userService) {
    this.tagRepository = tagRepository;
    this.userService = userService;
  }

  @Override
  public List<Tag> findAllByUserId(Long userId) {
    Optional<User> optionalUser = userService.findById(userId);
    if (optionalUser.isEmpty()) {
      return Collections.emptyList();
    }

    User user = optionalUser.get();
    return stream(tagRepository.findAllByUser(user))
        .sorted(comparing(Tag::getName))
        .collect(Collectors.toList());
  }

  @Override
  public List<Tag> searchByUserIdAndName(Long userId, String name) {
    Optional<User> optionalUser = userService.findById(userId);
    if (optionalUser.isEmpty()) {
      return Collections.emptyList();
    }

    User user = optionalUser.get();
    return stream(tagRepository.findByUserAndNameContainingIgnoreCase(user, name))
        .sorted(comparing(Tag::getName))
        .collect(Collectors.toList());
  }

  private static <T> Stream<T> stream(Iterable<T> iterable) {
    return StreamSupport.stream(iterable.spliterator(), false);
  }
}
