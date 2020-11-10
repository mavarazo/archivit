package com.mav.archivit.api.file;

import com.mav.archivit.model.File;
import com.mav.archivit.model.User;
import com.mav.archivit.repository.FileRepository;
import com.mav.archivit.repository.TagRepository;
import com.mav.archivit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Comparator.comparing;

@RestController
@RequestMapping("/api/file")
public class FileController {

  private final FileRepository fileRepository;
  private final TagRepository tagRepository;
  private final UserRepository userRepository;

  @Autowired
  public FileController(
      FileRepository fileRepository, TagRepository tagRepository, UserRepository userRepository) {
    this.fileRepository = fileRepository;
    this.tagRepository = tagRepository;
    this.userRepository = userRepository;
  }

  @RequestMapping("/")
  @CrossOrigin(origins = "http://localhost:4200")
  @ResponseBody
  public ResponseEntity<List<File>> findAll(@RequestParam(name = "userId") Long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (optionalUser.isEmpty()) {
      return ResponseEntity.ok(Collections.emptyList());
    }

    return ResponseEntity.ok(
        stream(fileRepository.findAllByUser(optionalUser.get()))
            .sorted(comparing(File::getPath))
            .collect(Collectors.toList()));
  }

  @RequestMapping("/{id}")
  @CrossOrigin(origins = "http://localhost:4200")
  @ResponseBody
  public ResponseEntity<File> findById(
      @PathVariable("id") Long id, @RequestParam(name = "userId") long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (optionalUser.isEmpty()) {
      return ResponseEntity.of(Optional.empty());
    }

    return ResponseEntity.of(
        fileRepository.findById(id).filter(file -> file.getUser() == optionalUser.get()));
  }

  @RequestMapping("/untagged")
  @CrossOrigin(origins = "http://localhost:4200")
  @ResponseBody
  public ResponseEntity<List<File>> findAllByTagsIsEmpty(
      @RequestParam(name = "userId") long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (optionalUser.isEmpty()) {
      return ResponseEntity.ok(Collections.emptyList());
    }

    return ResponseEntity.ok(
        stream(fileRepository.findAllByUser(optionalUser.get()))
            .filter(file -> file.getTags().isEmpty())
            .sorted(comparing(File::getPath))
            .collect(Collectors.toList()));
  }

  private static <T> Stream<T> stream(Iterable<T> iterable) {
    return StreamSupport.stream(iterable.spliterator(), false);
  }
}
