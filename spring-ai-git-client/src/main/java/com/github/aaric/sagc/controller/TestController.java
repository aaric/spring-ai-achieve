package com.github.aaric.sagc.controller;

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
 * 测试控制器
 *
 * @author Aaric
 * @version 0.6.0-SNAPSHOT
 */
@Slf4j
@RequiredArgsConstructor
@Tag(name = "测试模块API")
@RequestMapping("/api/test")
@RestController
public class TestController {

    @Operation(summary = "编译项目获取日志")
    @GetMapping(value = "/build")
    String build(@Parameter(description = "项目名称") @RequestParam String projectName) {
        return "ok";
    }
}
