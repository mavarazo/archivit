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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.stream.StreamSupport.stream;

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
  public ResponseEntity<List<Tag>> findAll(
      @RequestParam(name = "userId", required = true) long userId) {
    List<Tag> tags = new ArrayList<>();
    Optional<User> optionalUser = userRepository.findById(userId);
    optionalUser.ifPresent(
        user ->
            stream(tagRepository.findAllByUser(user).spliterator(), false)
                .sorted(comparing(Tag::getName))
                .forEach(tags::add));
    return ResponseEntity.ok(tags);
  }
}
