package com.mav.archivit;

import com.mav.archivit.model.File;
import com.mav.archivit.model.User;
import com.mav.archivit.repository.FileRepository;
import com.mav.archivit.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class ArchivitApplication {

  public static void main(String[] args) {
    SpringApplication.run(ArchivitApplication.class, args);
  }

  @Bean
  ApplicationRunner init(UserRepository userRepository, FileRepository fileRepository) {
    User marco = User.UserBuilder.anUser().withName("Marco").build();
    userRepository.save(marco);

    File rootFile =
        File.FileBuilder.aFile()
            .withName("File 01")
            .withPath("/Users/marco.niederberger/Desktop/File 01.pdf")
            .withCreated(Date.from(Instant.now()))
            .withUpdated(Date.from(Instant.now()))
            .withUser(marco)
            .withContent("Root File")
            .build();

    File folder =
        File.FileBuilder.aFile()
            .withName("Folder")
            .withPath("/Users/marco.niederberger/Desktop/File 01.pdf")
            .withCreated(Date.from(Instant.now()))
            .withUpdated(Date.from(Instant.now()))
            .withUser(marco)
            .build();

    File folderFile =
        File.FileBuilder.aFile()
            .withName("File 02")
            .withPath("/Users/marco.niederberger/Desktop/Folder/File 02.pdf")
            .withCreated(Date.from(Instant.now()))
            .withUpdated(Date.from(Instant.now()))
            .withUser(marco)
            .withParentFile(folder)
            .withContent("Lorem Ipsum")
            .build();

    return args -> Stream.of(rootFile, folder, folderFile).forEach(fileRepository::save);
  }
}
