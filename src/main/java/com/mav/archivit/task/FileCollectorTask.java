package com.mav.archivit.task;

import com.mav.archivit.model.File;
import com.mav.archivit.model.User;
import com.mav.archivit.repository.FileRepository;
import com.mav.archivit.repository.UserRepository;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BooleanSupplier;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class FileCollectorTask {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileCollectorTask.class);
  private final UserRepository userRepository;
  private final FileRepository fileRepository;

  @Autowired
  public FileCollectorTask(UserRepository userRepository, FileRepository fileRepository) {
    this.userRepository = userRepository;
    this.fileRepository = fileRepository;
  }

  // @Scheduled(cron = "* 0 * * * *")
  @Scheduled(fixedRate = 50000)
  public void scan() {
    LOGGER.info("Start scanner");
    userRepository.findAll().forEach(this::scanForFilesByUser);
  }

  private void scanForFilesByUser(User user) {
    try {
      long start = System.nanoTime();
      Files.walkFileTree(Path.of(user.getPath()), new MyFileVisitor(user, fileRepository));
      LOGGER.info(
          "Processed files for user '{}' in {} milliseconds",
          user.getName(),
          ((System.nanoTime() - start) / 1000000));
    } catch (IOException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  private static class MyFileVisitor extends SimpleFileVisitor<Path> {

    private final User user;
    private final FileRepository fileRepository;
    private final Map<String, File> directoryMap = new HashMap<>();

    public MyFileVisitor(User user, FileRepository fileRepository) {
      this.user = user;
      this.fileRepository = fileRepository;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
        throws IOException {
      if (FilenameUtils.getName(dir.toString()).startsWith(".")) {
        return FileVisitResult.CONTINUE;
      }

      File directory = newOrUpdateFile(dir, attrs);
      directory.setRegularFile(Boolean.FALSE);
      directoryMap.putIfAbsent(dir.toString(), fileRepository.save(directory));
      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
      if (FilenameUtils.getName(file.toString()).startsWith(".")) {
        return FileVisitResult.CONTINUE;
      }

      File directory = newOrUpdateFile(file, attrs);
      directory.setRegularFile(Boolean.TRUE);
      fileRepository.save(directory);
      return FileVisitResult.CONTINUE;
    }

    private File newOrUpdateFile(Path path, BasicFileAttributes attrs) {
      File file =
          fileRepository
              .findByFileKey(attrs.fileKey().toString())
              .orElseGet(
                  File.FileBuilder.aFile()
                          .withUser(user)
                          .withCreationTime(toLocalDateTime(attrs.creationTime()))
                          .withFileKey(attrs.fileKey().toString())
                      ::build);

      isNullOrChanged(file.getName(), FilenameUtils.getBaseName(path.toString()))
          .ifPresent(file::setName);
      isNullOrChanged(file.getExtension(), FilenameUtils.getExtension(path.toString()))
          .ifPresent(file::setExtension);
      isNullOrChanged(file.getPath(), path.toString()).ifPresent(file::setPath);
      isNullOrChanged(file.getLastAccessTime(), toLocalDateTime(attrs.lastAccessTime()))
          .ifPresent(file::setLastAccessTime);
      isNullOrChanged(file.getLastModifiedTime(), toLocalDateTime(attrs.lastModifiedTime()))
          .ifPresent(file::setLastModifiedTime);
      isNullOrChanged(file.getSize(), attrs.size()).ifPresent(file::setSize);

      getFromDirectoryMap(path.getParent().toString())
          .flatMap(newParent -> isNullOrChanged(file.getParentFile(), newParent))
          .ifPresent(file::setParentFile);

      getContent(path)
          .flatMap(newContent -> isNullOrChanged(file.getContent(), newContent))
          .ifPresent(file::setContent);

      return file;
    }

    private Optional<File> getFromDirectoryMap(String key) {
      return Optional.ofNullable(directoryMap.getOrDefault(key, null));
    }

    private Optional<String> getContent(Path path) {
      if (!"pdf".equalsIgnoreCase(FilenameUtils.getExtension(path.toString()))) {
        return Optional.empty();
      }

      try (PDDocument document = PDDocument.load(path.toFile())) {
        if (!document.isEncrypted()) {
          PDFTextStripper pdfTextStripper = new PDFTextStripper();
          return Optional.of(pdfTextStripper.getText(document));
        }
      } catch (IOException e) {
        LOGGER.error(e.getMessage(), e);
      }

      return Optional.empty();
    }

    private static Optional<String> isNullOrChanged(String currentValue, String newValue) {
      return isNullOrChanged(currentValue, newValue, () -> !currentValue.equals(newValue));
    }

    private static Optional<LocalDateTime> isNullOrChanged(
        LocalDateTime currentValue, LocalDateTime newValue) {
      return isNullOrChanged(currentValue, newValue, () -> !currentValue.isEqual(newValue));
    }

    private static Optional<Long> isNullOrChanged(long currentValue, long newValue) {
      return isNullOrChanged(currentValue, newValue, () -> currentValue != newValue);
    }

    private static Optional<File> isNullOrChanged(File currentValue, File newValue) {
      return isNullOrChanged(
          currentValue, newValue, () -> currentValue.getFileKey().equals(newValue.getFileKey()));
    }

    private static <T> Optional<T> isNullOrChanged(
        T currentValue, T newValue, BooleanSupplier isChanged) {
      if (isNull(currentValue) && nonNull(newValue)) {
        return Optional.of(newValue);
      }
      return isChanged.getAsBoolean() ? Optional.ofNullable(newValue) : Optional.empty();
    }

    private static LocalDateTime toLocalDateTime(FileTime value) {
      return LocalDateTime.ofInstant(value.toInstant(), ZoneId.systemDefault());
    }
  }
}
