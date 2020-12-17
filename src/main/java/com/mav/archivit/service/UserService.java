package com.mav.archivit.service;

import com.mav.archivit.model.User;

import java.util.Optional;

public interface UserService {
   Optional<User> findById(Long id);
}
