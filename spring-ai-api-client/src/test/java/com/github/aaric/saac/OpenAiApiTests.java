package com.github.aaric.saac;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * OpenAiApiTests
 *
 * @author Aaric
 * @version 0.1.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class OpenAiApiTests {

    private final ChatClient chatClient;

    @Test
    public void testPromptText() {
//        System.err.println(chatClient.prompt().user("今天武汉天气咋样？").call().content());
        System.err.println(chatClient.prompt().user("今天武汉下雨，请给岀行建议？").call().content());
    }
}
