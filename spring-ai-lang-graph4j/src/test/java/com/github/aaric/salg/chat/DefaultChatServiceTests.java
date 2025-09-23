package com.github.aaric.salg.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * DefaultChatServiceTests
 *
 * @author Aaric
 * @version 0.22.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class DefaultChatServiceTests {

    private final DefaultChatService defaultChatService;

    @Test
    public void testChatWithJoke() {
        log.debug("{}", defaultChatService.chatWithJoke(1, "讲个笑话").blockFirst());
    }
}
