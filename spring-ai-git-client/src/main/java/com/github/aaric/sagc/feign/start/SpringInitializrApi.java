package com.github.aaric.sagc.feign.start;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Spring项目初始化 Api
 *
 * @author Aaric
 * @version 0.7.0-SNAPSHOT
 */
@Tag(name = "Spring Initializr Api")
public interface SpringInitializrApi {

    @Operation(summary = "初始化Java项目")
    @GetMapping("/starter.zip")
    ResponseEntity<byte[]> starterZip(@Parameter(description = "项目类型：gradle-project、maven-project") @RequestParam String type,
                                      @Parameter(description = "项目语言：java、kotlin、groovy") @RequestParam String language,
                                      @Parameter(description = "Spring Boot版本，例如：2.3.12.RELEASE") @RequestParam String bootVersion,
                                      @Parameter(description = "项目目录，例如：demo") @RequestParam String baseDir,
                                      @Parameter(description = "组标识，例如：com.example") @RequestParam String groupId,
                                      @Parameter(description = "项目标识，例如：demo") @RequestParam String artifactId,
                                      @Parameter(description = "项目名称，例如：demo") @RequestParam String name,
                                      @Parameter(description = "项目描述，例如：Demo project for Spring Boot") @RequestParam String description,
                                      @Parameter(description = "项目包名称，例如：com.example.demo") @RequestParam String packageName,
                                      @Parameter(description = "项目打包方式，例如：jar") @RequestParam String packaging,
                                      @Parameter(description = "Java版本，例如：1.8/11/17/21") @RequestParam String javaVersion,
                                      @Parameter(description = "Java依赖，例如：web") @RequestParam String dependencies);
}
