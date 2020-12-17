package com.mav.archivit.service;

import com.mav.archivit.model.Tag;
import com.mav.archivit.repository.TagRepository;
import com.mav.archivit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class TagServiceImplTest {

  @MockBean TagRepository tagRepository;

  @MockBean UserService userService;

  @Autowired TagServiceImpl sut;

  @Test
  void testFindAllByUserId_user_does_not_exists() {
    // Arrange
    doReturn(Optional.empty()).when(userService).findById(any());

    // Act
    List<Tag> result = sut.findAllByUserId(1L);

    // Assert
    assertTrue(result.isEmpty());
  }

  @Test
  void testFindAllByUserId() {
    // Arrange
    doReturn(Optional.empty()).when(userService).findById(any());
    doReturn(Tag.TagBuilder.aTag().build()).when(tagRepository).findAllByUser(any());

    // Act
    List<Tag> result = sut.findAllByUserId(1L);

    // Assert
    assertTrue(result.isEmpty());
  }
}
