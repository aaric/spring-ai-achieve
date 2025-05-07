package com.sample.saac;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ApiClientApp
 *
 * @author Aaric
 * @version 0.1.0-SNAPSHOT
 */
@SpringBootApplication
@RequiredArgsConstructor
public class ApiClientApp {

    private final ChatClient chatClient;

    public static void main(String[] args) {
        SpringApplication.run(ApiClientApp.class, args);
    }
}
