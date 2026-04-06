package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlagiarismApplication {
    public static void main(String[] args) {
        // This line starts the Tomcat server on port 8080
        SpringApplication.run(PlagiarismApplication.class, args);
    }
}