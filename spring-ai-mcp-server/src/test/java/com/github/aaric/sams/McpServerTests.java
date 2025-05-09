package com.github.aaric.sams;

import com.github.aaric.sams.amap.CityHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

/**
 * McpServerTests
 *
 * @author Aaric
 * @version 0.2.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class McpServerTests {

    private final RestTemplate restTemplate;

    @Test
    public void testGetCityCode() {
        System.err.println(CityHelper.getCityCode("武汉"));
    }

    @Test
    public void testGetCityWeather() {
        System.err.println(restTemplate);
    }
}
