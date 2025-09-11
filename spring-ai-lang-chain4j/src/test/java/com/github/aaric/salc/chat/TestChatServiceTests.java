package com.github.aaric.salc.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * TestChatServiceTests
 *
 * @author Aaric
 * @version 0.17.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class TestChatServiceTests {

    private final TestChatService testChatService;

    @Test
    public void testChatOne() {
        log.info(testChatService.chatOne("讲个笑话"));
    }

    @Test
    public void testChatTwo() {
        log.info(testChatService.chatTwo("讲个笑话"));
    }
}
