package com.github.aaric.salg;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * StringRedisTemplateTests
 *
 * @author Aaric
 * @version 0.1.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class StringRedisTemplateTests {

    private final StringRedisTemplate stringRedisTemplate;

    @Test
    public void testInsert() {
        stringRedisTemplate.opsForValue().set("test", "test");
    }
}
