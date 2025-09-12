package com.github.aaric.salg;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * LangGraph4jTests
 *
 * @author Aaric
 * @version 0.18.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class LangGraph4jTests {

    @Test
    public void testPromptText() {
        log.debug("{}", "hello world");
    }
}
