package com.github.aaric.sadc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * DifyClientTests
 *
 * @author Aaric
 * @version 0.5.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class DifyClientTests {

    @Test
    public void testPrint() {
        System.err.println("Hello World!");
    }
}
