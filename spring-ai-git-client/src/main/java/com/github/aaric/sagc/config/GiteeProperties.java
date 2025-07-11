package com.github.aaric.sagc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Gitee API 调用配置
 *
 * @author Aaric
 * @version 0.6.0-SNAPSHOT
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "git.gitee")
public class GiteeProperties {

    private String endpointUrl;

    private String accessToken;

    private String testOwner;

    private String testRepo;
}
