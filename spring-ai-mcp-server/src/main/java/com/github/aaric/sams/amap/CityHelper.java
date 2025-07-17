package com.github.aaric.sams.amap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * CityHelper
 *
 * @author Aaric
 * @version 0.4.0-SNAPSHOT
 */
public class CityHelper {

    private final static List<City> CITY_LOADED_LIST = new ArrayList<>();

    public record City(@JsonProperty("name") String name,
                       @JsonProperty("adname") String adname,
                       @JsonProperty("adcode") String adcode,
                       @JsonProperty("citycode") String citycode) {

    }

    static {
        try (InputStream is = new ClassPathResource("amap/city.json").getInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            List<City> cities = mapper.readValue(is, mapper.getTypeFactory().constructCollectionType(List.class, City.class));
            if (null != cities && !cities.isEmpty()) {
                CITY_LOADED_LIST.addAll(cities);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getCityCode(String cityName) {
        if (StringUtils.isNotEmpty(cityName)) {
            String searchName = cityName.trim();
            for (City city : CITY_LOADED_LIST) {
                if (city.name().contains(searchName) && StringUtils.isNotEmpty(city.citycode)) {
                    return city.adcode();
                }
            }
        }
        return null;
    }

    public static Map<String, String> getCityCodeMap(String cityName) {
        if (StringUtils.isNotEmpty(cityName)) {
            String searchName = cityName.trim();
            if (!CITY_LOADED_LIST.isEmpty()) {
                return CITY_LOADED_LIST.stream().filter(city -> city.adname().contains(searchName))
                        .collect(Collectors.toMap(City::adname, City::adcode));
            }
        }
        return null;
    }
}
