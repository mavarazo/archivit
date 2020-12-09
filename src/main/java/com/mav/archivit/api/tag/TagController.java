package com.mav.archivit.api.tag;

import com.mav.archivit.model.Tag;
import com.mav.archivit.model.User;
import com.mav.archivit.repository.TagRepository;
import com.mav.archivit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Comparator.comparing;

@RestController
@RequestMapping("/api/tag")
public class TagController {

  private final TagRepository tagRepository;
  private final UserRepository userRepository;

  @Autowired
  public TagController(TagRepository tagRepository, UserRepository userRepository) {
    this.tagRepository = tagRepository;
    this.userRepository = userRepository;
  }

  @RequestMapping("/")
  @CrossOrigin(origins = "http://localhost:4200")
  @ResponseBody
  public ResponseEntity<List<TagDto>> findAll(@RequestParam(name = "userId") Long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (optionalUser.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    User user = optionalUser.get();
    return ResponseEntity.ok(
        stream(tagRepository.findAllByUser(user))
            .sorted(comparing(Tag::getName))
            .map(tag -> ImmutableTagDto.builder().id(user.getId()).name(user.getName()).build())
            .collect(Collectors.toList()));
  }

  @RequestMapping("/search")
  @CrossOrigin(origins = "http://localhost:4200")
  @ResponseBody
  public ResponseEntity<List<TagDto>> search(
      @RequestParam(name = "userId") Long userId, @RequestParam(name = "name") String name) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (optionalUser.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    User user = optionalUser.get();
    return ResponseEntity.ok(
        stream(tagRepository.findByUserAndNameContainingIgnoreCase(user, name))
            .sorted(comparing(Tag::getName))
            .map(tag -> ImmutableTagDto.builder().id(tag.getId()).name(tag.getName()).build())
            .collect(Collectors.toList()));
  }

  private static <T> Stream<T> stream(Iterable<T> iterable) {
    return StreamSupport.stream(iterable.spliterator(), false);
  }
}
