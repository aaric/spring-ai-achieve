package com.github.aaric.sads.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * HostProperties
 *
 * @author Aaric
 * @version 0.12.0-SNAPSHOT
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "host")
public class HostProperties {

    private String intranetIp;

    private Integer sshPort;

    private String username;

    private String password;

    private Integer dockerPort;

    private String dockerCertsPath;
}
