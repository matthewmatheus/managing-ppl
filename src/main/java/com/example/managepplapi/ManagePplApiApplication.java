package com.example.managepplapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class ManagePplApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagePplApiApplication.class, args);
    }

}
