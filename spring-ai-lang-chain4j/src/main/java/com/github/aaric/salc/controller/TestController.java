package com.github.aaric.salc.controller;

import com.github.aaric.salc.chat.TestChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * 测试模块API控制器
 * <br>
 * <code>
 * curl -X GET -G "http://localhost:8082/api/test/chat/six" --data-urlencode "memoryId=1" --data-urlencode "question=讲个笑话"
 * </code>
 *
 * @author Aaric
 * @version 0.17.0-SNAPSHOT
 */
@Tag(name = "测试模块API", description = "测试学习示例相关操作")
@RequestMapping("/api/test")
@RestController
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final TestChatService testChatService;

    @Operation(summary = "聊天示例6", description = "测试SSE流式输出")
    @GetMapping("/chat/six")
    public Flux<ServerSentEvent<String>> chatSix(@Parameter(description = "会话ID", example = "1") @RequestParam int memoryId,
                                                 @Parameter(description = "用户提问", example = "讲个笑话") @RequestParam String question) {
        log.info("chatSix -> memoryId={}, question={}", memoryId, question);
        return testChatService.chatSix(memoryId, question)
                .map(chunk -> ServerSentEvent.<String>builder().data(chunk).build());
    }
}
