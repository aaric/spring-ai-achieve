package com.github.aaric.salg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.aaric.salg.chat.DefaultChatService;
import com.github.aaric.salg.graph.OpinionWorkflowGraph;
import com.github.aaric.salg.ws.ReactiveWebSocketHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * 测试模块API控制器
 *
 * @author Aaric
 * @version 0.22.0-SNAPSHOT
 */
@Tag(name = "示例模块API", description = "示例Demo")
@RequestMapping("/api/demo")
@RestController
@Slf4j
@RequiredArgsConstructor
public class DemoController {

    private final ObjectMapper objectMapper;

    private final DefaultChatService defaultChatService;

    private final OpinionWorkflowGraph opinionWorkflowGraph;

    private final StringRedisTemplate stringRedisTemplate;

    private final ReactiveWebSocketHandler webSocketHandler;

    @Operation(summary = "聊天接口", description = "简单测试一下")
    @GetMapping("/chat")
    public Flux<ServerSentEvent<String>> chat(@Parameter(description = "会话ID", example = "1") @RequestParam int memoryId,
                                              @Parameter(description = "用户提问", example = "讲个笑话") @RequestParam String question) {
        log.info("chat -> memoryId={}, question={}", memoryId, question);
        return defaultChatService.chatWithJoke(memoryId, question)
                .map(chunk -> ServerSentEvent.<String>builder().data(chunk).build());
    }
}
