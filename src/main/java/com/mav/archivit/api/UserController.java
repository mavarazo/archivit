package com.mav.archivit.api;

import com.mav.archivit.model.User;
import com.mav.archivit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserRepository userRepository;

  @Autowired
  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @RequestMapping("/")
  @ResponseBody
  public ResponseEntity<List<User>> index() {
    List<User> users = (List<User>) userRepository.findAll();
    return ResponseEntity.ok(users);
  }
}
