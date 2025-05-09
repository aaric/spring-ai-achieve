package com.github.aaric.sams.amap.respapi.v3.weather;

import lombok.Data;

/**
 * WeatherLive
 *
 * @author sripe
 * @version 0.4.1-SNAPSHOT
 */
@Data
public class WeatherLive {

    // 省份
    private String province;
    // 城市
    private String city;
    // 区域编码
    private String adcode;
    // 天气
    private String weather;
    // 气温
    private String temperature;
    // 气温（浮点）
    private String temperature_float;
    // 风向
    private String winddirection;
    // 风力级别
    private String windpower;
    // 空气湿度
    private String humidity;
    // 空气湿度（浮点）
    private String humidity_float;
    // 报告时间
    private String reporttime;
}
