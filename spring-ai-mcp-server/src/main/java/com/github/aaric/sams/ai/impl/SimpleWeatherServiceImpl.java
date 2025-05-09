package com.github.aaric.sams.ai.impl;

import com.github.aaric.sams.ai.SimpleWeatherService;
import com.github.aaric.sams.amap.CityHelper;
import com.github.aaric.sams.amap.respapi.v3.weather.WeatherLive;
import com.github.aaric.sams.amap.respapi.v3.weather.WeatherLiveResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * SimpleWeatherServiceImpl
 *
 * @author Aaric
 * @version 0.4.0-SNAPSHOT
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleWeatherServiceImpl implements SimpleWeatherService {

    @Value("${amap.base-url}")
    private String baseUrl;

    @Value("${amap.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @Override
    @Tool(description = "根据城市名称名称获取城市代码")
    public String cityCode(@ToolParam(description = "城市名称，如北京、上海") String cityName) {
        System.err.println("cityCode");
        return CityHelper.getCityCode(cityName);
    }

    @Override
    @Tool(description = "根据城市城市代码获取城市的实时天气情况")
    public String cityWeather(@ToolParam(description = "城市代码，如北京110000、上海310000") String cityCode) {
        System.err.println("cityWeather");
        String weatherUrl = String.format("%s/v3/weather/weatherInfo?key=%s&extensions=base&city=%s", baseUrl, apiKey, "420100");
        ResponseEntity<WeatherLiveResponse> response = restTemplate.getForEntity(weatherUrl, WeatherLiveResponse.class);
        if ("OK".equals(response.getBody().getInfo()) && ObjectUtils.isNotEmpty(response.getBody().getLives())) {
            WeatherLive weatherLive = response.getBody().getLives().get(0);
            return String.format("%s天气情况：%s，温度%s℃，风向%s，风力%s，湿度%s。",
                    weatherLive.getCity(), weatherLive.getWeather(), weatherLive.getTemperature_float(),
                    weatherLive.getWinddirection(), weatherLive.getWindpower(), weatherLive.getHumidity_float());
        }
        return "抱歉，无法查询该地区的天气情况！";
    }
}
