package com.mav.archivit.repository;

import com.mav.archivit.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {}
