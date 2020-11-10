package com.mav.archivit.repository;

import com.mav.archivit.model.File;
import com.mav.archivit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {

  Iterable<File> findAllByUser(User user);

  Optional<File> findByFileKey(String filekey);
}
