package com.github.aaric.sagc;

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
 * @version 0.6.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class OpenAiApiTests {

    private final ChatClient chatClient;

    private final ChatClient.Builder builder;

    @Test
    public void testPromptText() {
        System.err.println(chatClient.prompt().user("Git最新版本是啥？").call().content());
    }

    @Test
    public void testPromptText2() {
        ChatClient chatClient2 = builder.defaultSystem("""
                        你是一个经验丰富的Java程序员，能够识别和分析代码，并按照要求给代码添加合理的注释。
                        """)
                .build();
        System.err.println(chatClient2.prompt().user("Git最新版本是啥？").call().content());
    }
}
