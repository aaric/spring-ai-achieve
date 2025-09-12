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
        log.debug(testChatService.chatOne("讲个笑话"));
    }

    @Test
    public void testChatTwo() {
//        log.debug(testChatService.chatTwo("讲个笑话"));
        log.debug(testChatService.chatTwo("讲个关于死亡笑话"));
    }

    @Test
    public void testChatThreeWithMemory() {
        String result = testChatService.chatThree(1, "你好，我是一名Java程序员。讲个职场笑话");
        log.debug(result);
        result = testChatService.chatThree(1, "你好，我是谁来着？");
        log.debug(result);
        result = testChatService.chatThree(2, "你好，我是谁来着？");
        log.debug(result);
    }

    @Test
    public void testChatFour() {
        log.debug("{}", testChatService.chatFour("讲个笑话，不要太冷"));
    }

    @Test
    public void testChatFive() {
        log.debug("{}", testChatService.chatFive("搜索一下今天的热点新闻"));
    }
}
