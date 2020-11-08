package com.mav.archivit.repository;

import com.mav.archivit.model.File;
import com.mav.archivit.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FileRepository extends CrudRepository<File, Long> {

  Iterable<File> findAllByUser(User user);

  Optional<File> findByFileKey(String filekey);
}
