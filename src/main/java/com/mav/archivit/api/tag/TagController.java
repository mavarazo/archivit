package com.mav.archivit.api.tag;

import com.mav.archivit.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tag")
public class TagController {

  private final TagService tagService;

  @Autowired
  public TagController(TagService tagService) {
    this.tagService = tagService;
  }

  @RequestMapping("/")
  @CrossOrigin(origins = "http://localhost:4200")
  @ResponseBody
  public ResponseEntity<List<TagDto>> findAll(@RequestParam(name = "userId") Long userId) {
    return ResponseEntity.ok(
        tagService.findAllByUserId(userId).stream()
            .map(tag -> ImmutableTagDto.builder().id(tag.getId()).name(tag.getName()).build())
            .collect(Collectors.toList()));
  }

  @RequestMapping("/search")
  @CrossOrigin(origins = "http://localhost:4200")
  @ResponseBody
  public ResponseEntity<List<TagDto>> search(
      @RequestParam(name = "userId") Long userId, @RequestParam(name = "name") String name) {
    return ResponseEntity.ok(tagService.searchByUserIdAndName(userId, name).stream()
          .map(tag -> ImmutableTagDto.builder().id(tag.getId()).name(tag.getName()).build())
          .collect(Collectors.toList()));
  }
}
