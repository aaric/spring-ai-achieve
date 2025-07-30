package com.github.aaric.sadc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * DifyProperties
 *
 * @author Aaric
 * @version 0.14.0-SNAPSHOT
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "dify")
public class DifyProperties {

    private String baseUrl;

    private Map<String, String> appKey;
}
