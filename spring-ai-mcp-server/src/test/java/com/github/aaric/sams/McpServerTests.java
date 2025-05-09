package com.github.aaric.sams;

import com.github.aaric.sams.amap.CityHelper;
import com.github.aaric.sams.amap.respapi.v3.weather.WeatherLiveResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
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

    @Value("${amap.base-url}")
    private String baseUrl;

    @Value("${amap.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @Test
    public void testGetCityCode() {
        System.err.println(CityHelper.getCityCode("武汉"));
    }

    @Test
    public void testGetCityWeather() {
        String weatherUrl = String.format("%s/v3/weather/weatherInfo?key=%s&extensions=base&city=%s", baseUrl, apiKey, "420100");
        ResponseEntity<WeatherLiveResponse> response = restTemplate.getForEntity(weatherUrl, WeatherLiveResponse.class);
        System.err.println(response.getBody().getLives().get(0).getWeather());
    }
}
