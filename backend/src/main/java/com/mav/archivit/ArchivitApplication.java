package com.mav.archivit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ArchivitApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArchivitApplication.class, args);
	}

}
