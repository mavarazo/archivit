package com.mav.archivit.api;

import com.mav.archivit.model.File;
import com.mav.archivit.model.User;
import com.mav.archivit.repository.FileRepository;
import com.mav.archivit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/files")
public class FileController {

  private final FileRepository fileRepository;
  private final UserRepository userRepository;

  @Autowired
  public FileController(FileRepository fileRepository, UserRepository userRepository) {
    this.fileRepository = fileRepository;
    this.userRepository = userRepository;
  }

  @RequestMapping("/")
  @CrossOrigin(origins = "http://localhost:4200")
  @ResponseBody
  public ResponseEntity<List<File>> index(
      @RequestParam(name = "userId", required = true) long userId) {
    List<File> files = new ArrayList<>();
    Optional<User> optionalUser = userRepository.findById(userId);
    optionalUser.ifPresent(user -> fileRepository.findAllByUser(user).forEach(files::add));
    return ResponseEntity.ok(files);
  }
}
