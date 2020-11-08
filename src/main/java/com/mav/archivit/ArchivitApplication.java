package com.mav.archivit;

import com.mav.archivit.model.User;
import com.mav.archivit.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.stream.Stream;

@SpringBootApplication
@EnableScheduling
public class ArchivitApplication {

  public static void main(String[] args) {
    SpringApplication.run(ArchivitApplication.class, args);
  }

  @Bean
  ApplicationRunner init(UserRepository userRepository) {
    return args ->
        Stream.of(
                User.UserBuilder.anUser()
                    .withName("Marco")
                    .withPath("/Users/marco.niederberger/Nextcloud/Asgard/Archive")
                    .build())
            .forEach(userRepository::save);
  }
}
