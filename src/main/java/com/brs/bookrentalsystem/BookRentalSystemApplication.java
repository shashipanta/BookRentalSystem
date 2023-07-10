package com.brs.bookrentalsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.brs.bookrentalsystem.repo")
public class BookRentalSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookRentalSystemApplication.class, args);
    }

}
