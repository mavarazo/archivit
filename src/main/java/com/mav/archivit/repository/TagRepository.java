package com.mav.archivit.repository;

import com.mav.archivit.model.Tag;
import com.mav.archivit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

  Iterable<Tag> findAllByUser(User user);

  Optional<Tag> findByNameIgnoreCase(String name);

  Iterable<Tag> findByUserAndNameContainingIgnoreCase(User user, String name);
}
