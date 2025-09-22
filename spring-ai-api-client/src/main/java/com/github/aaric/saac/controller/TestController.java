package com.github.aaric.saac.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试模块API
 *
 * @author Aaric
 * @version 0.21.0-SNAPSHOT
 */
@Tag(name = "测试模块API", description = "简单测试一下")
@RequestMapping("/api/test")
@RestController
@Slf4j
@RequiredArgsConstructor
public class TestController {

    @Operation(summary = "打招呼", description = "简单测试一下")
    @GetMapping("/say/hello")
    public String sayHello(@Parameter(description = "会话ID", example = "Nick") @RequestParam String name) {
        log.info("sayHello -> name={}", name);
        return "Hello, %s!".formatted(name);
    }
}
