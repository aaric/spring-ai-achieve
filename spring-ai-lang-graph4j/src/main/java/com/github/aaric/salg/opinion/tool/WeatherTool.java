package com.github.aaric.salg.opinion.tool;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * WeatherTool
 *
 * @author Aaric
 * @version 0.20.0-SNAPSHOT
 */
@Slf4j
@Component
public class WeatherTool {

    @Tool("获取指定城市的天气情况")
    public static String getWeather(@P("城市名称") String cityName) {
//        return "今天" + cityName + "的天气是晴天";
        return "今天" + cityName + "的天气是下雨";
    }
}
