package com.mav.archivit.repository;

import com.mav.archivit.model.Tag;
import com.mav.archivit.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TagRepository extends CrudRepository<Tag, Long> {

  Iterable<Tag> findAllByUser(User user);

  Optional<Tag> findByNameIgnoreCase(String name);
}
