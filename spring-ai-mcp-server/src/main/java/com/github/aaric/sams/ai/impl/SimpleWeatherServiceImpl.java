package com.github.aaric.sams.ai.impl;

import com.github.aaric.sams.ai.SimpleWeatherService;
import com.github.aaric.sams.amap.CityHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

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

    @Override
    @Tool(description = "根据城市名称名称获取城市代码")
    public String cityCode(@ToolParam(description = "城市名称，如北京、上海") String cityName) {
        System.err.println("cityCode");
        return CityHelper.getCityCode(cityName);
    }

//    @Override
//    @Tool(description = "根据城市城市代码获取城市的实时天气情况")
//    public String cityWeather(@ToolParam(description = "城市代码，如北京110000、上海310000") String cityCode) {
//        System.err.println("cityWeather");
//        return "";
//    }
}
