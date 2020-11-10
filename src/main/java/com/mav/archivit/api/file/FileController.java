package com.mav.archivit.api.file;

import com.mav.archivit.model.File;
import com.mav.archivit.model.Tag;
import com.mav.archivit.model.User;
import com.mav.archivit.repository.FileRepository;
import com.mav.archivit.repository.TagRepository;
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
import java.util.Objects;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.stream.StreamSupport.stream;

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
  public ResponseEntity<List<File>> findAll(
      @RequestParam(name = "userId") Long userId,
      @RequestParam(name = "tagId", required = false) Long tagId) {
    List<File> files = new ArrayList<>();
    Optional<User> optionalUser = userRepository.findById(userId);
    Optional<Tag> optionalTag = findTagById(tagId);

    optionalUser.ifPresent(
        user ->
            stream(fileRepository.findAllByUser(user).spliterator(), false)
                .filter(
                    file ->
                        optionalTag.isEmpty()
                            || file.getTags().stream()
                                .anyMatch(tag -> tag.getId().equals(optionalTag.get().getId())))
                .sorted(comparing(File::getPath))
                .forEach(files::add));
    return ResponseEntity.ok(files);
  }

  private Optional<Tag> findTagById(Long tagId) {
    if (Objects.isNull(tagId)) {
      return Optional.empty();
    }

    return Optional.ofNullable(
        tagRepository
            .findById(tagId)
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        String.format("Tag '%d' does not exists.", tagId))));
  }

  @RequestMapping("/untagged")
  @CrossOrigin(origins = "http://localhost:4200")
  @ResponseBody
  public ResponseEntity<List<File>> findAllByTagsIsEmpty(
      @RequestParam(name = "userId", required = true) long userId) {
    List<File> files = new ArrayList<>();
    Optional<User> optionalUser = userRepository.findById(userId);
    optionalUser.ifPresent(
        user ->
            stream(fileRepository.findAllByUser(user).spliterator(), false)
                .filter(file -> file.getTags().isEmpty())
                .sorted(comparing(File::getPath))
                .forEach(files::add));
    return ResponseEntity.ok(files);
  }
}
