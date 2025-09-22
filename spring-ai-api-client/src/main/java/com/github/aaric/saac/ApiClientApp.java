package com.github.aaric.saac;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ApiClientApp
 *
 * @author Aaric
 * @version 0.1.0-SNAPSHOT
 */
@OpenAPIDefinition(
        info = @Info(
                title = "在线API文档",
                version = "1.0.0",
                description = "基于 SpringAI 开发",
                contact = @Contact(
                        name = "开发团队",
                        email = "vipaaric@github.com",
                        url = "https://github.com/aaric"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8081",
                        description = "开发环境"
                )
        }
)
@SpringBootApplication
public class ApiClientApp {

    public static void main(String[] args) {
        SpringApplication.run(ApiClientApp.class, args);
    }
}
