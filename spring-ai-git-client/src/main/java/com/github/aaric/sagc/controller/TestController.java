package com.github.aaric.sagc.controller;

import cn.hutool.core.util.RuntimeUtil;
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

    @Operation(summary = "在终端执行编译命令")
    @GetMapping(value = "/execute")
    String execute(@Parameter(description = "执行命令") @RequestParam String command) {
        String output = RuntimeUtil.execForStr(command);
        log.info("execute -> command={}, output={}", command, output);
        return output;
    }
}
