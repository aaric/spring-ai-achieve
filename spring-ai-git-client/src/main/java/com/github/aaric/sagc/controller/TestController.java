package com.github.aaric.sagc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

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
//        String output = RuntimeUtil.execForStr(command);
//        log.info("execute -> command={}, output={}", command, output);
        String output = "";
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ByteArrayOutputStream errorOutputStream = new ByteArrayOutputStream()) {
            CommandLine commandLine = CommandLine.parse(command);
            DefaultExecutor executor = DefaultExecutor.builder().get();
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorOutputStream);
            executor.setStreamHandler(streamHandler);
            int exitValue = executor.execute(commandLine);
            log.info("exitValue: {}", exitValue);
            output += outputStream.toString();
            output += errorOutputStream.toString();
        } catch (Exception e) {
            log.error("execute error", e);
        }
        log.info("execute -> command={}, output={}", command, output);
        return output;
    }
}
