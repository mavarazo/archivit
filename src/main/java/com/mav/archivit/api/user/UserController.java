package com.mav.archivit.api.user;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/user")
public class UserController {

  private final UserRepository userRepository;
  private final FileRepository fileRepository;
  private final TagRepository tagRepository;

  @Autowired
  public UserController(
      UserRepository userRepository, FileRepository fileRepository, TagRepository tagRepository) {
    this.userRepository = userRepository;
    this.fileRepository = fileRepository;
    this.tagRepository = tagRepository;
  }

  @RequestMapping("/")
  @CrossOrigin(origins = "http://localhost:4200")
  @ResponseBody
  public ResponseEntity<List<UserDto>> index() {
    return ResponseEntity.ok(
        stream(userRepository.findAll())
            .map(user -> ImmutableUserDto.builder().id(user.getId()).name(user.getName()).build())
            .collect(Collectors.toList()));
  }

  @RequestMapping("/{id}")
  @CrossOrigin(origins = "http://localhost:4200")
  @ResponseBody
  public ResponseEntity<UserDto> findById(@PathVariable("id") Long id) {
    Optional<User> optionalUser = userRepository.findById(id);
    if (optionalUser.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    User user = optionalUser.get();
    Iterable<File> files = fileRepository.findAllByUser(user);

    return ResponseEntity.ok(
        ImmutableUserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .totalFiles(stream(files).count())
            .totalUntaggedFiles(stream(files).filter(file -> file.getTags().isEmpty()).count())
            .totalStarredFiles(stream(files).filter(File::getStarred).count())
            .totalTags(stream(tagRepository.findAllByUser(user)).count())
            .addAllExtensions(stream(files).map(File::getExtension).collect(Collectors.toSet()))
            .build());
  }

  private static <T> Stream<T> stream(Iterable<T> iterable) {
    return StreamSupport.stream(iterable.spliterator(), false);
  }
}
