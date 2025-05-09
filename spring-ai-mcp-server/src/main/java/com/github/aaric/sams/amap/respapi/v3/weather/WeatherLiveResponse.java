package com.github.aaric.sams.amap.respapi.v3.weather;

import lombok.Data;

import java.util.List;

/**
 * RestResponse
 *
 * @author Aaric
 * @version 0.4.1-SNAPSHOT
 */
@Data
public class WeatherLiveResponse {

    private String status;
    private String count;
    private String info;
    private String infocode;
    private List<WeatherLive> lives;
}
