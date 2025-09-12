package com.github.aaric.salc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * LangChain4jApp
 *
 * @author Aaric
 * @version 0.16.0-SNAPSHOT
 */
@OpenAPIDefinition(
        info = @Info(
                title = "WebFlux API - Knife4j",
                version = "1.0.0",
                description = "基于 Spring WebFlux 和 Knife4j 的 RESTful API 文档",
                contact = @Contact(
                        name = "开发团队",
                        email = "dev@example.com",
                        url = "https://example.com"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8082",
                        description = "开发环境"
                )
        }
)
@SpringBootApplication
public class LangChain4jApp {

    public static void main(String[] args) {
        SpringApplication.run(LangChain4jApp.class, args);
    }
}
