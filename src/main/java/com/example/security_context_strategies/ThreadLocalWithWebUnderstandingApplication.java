package com.example.security_context_strategies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableAsync
public class ThreadLocalWithWebUnderstandingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreadLocalWithWebUnderstandingApplication.class, args);
    }

}
