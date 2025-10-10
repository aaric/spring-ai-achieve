package com.github.aaric.salg.controller;

import com.github.aaric.salg.chat.DefaultChatService;
import com.github.aaric.salg.opinion.graph.OpinionWorkflowGraph;
import com.github.aaric.salg.util.RequestIdUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * 示例模块API控制器
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

    private final DefaultChatService defaultChatService;

    private final OpinionWorkflowGraph opinionWorkflowGraph;

    @Schema(description = "请求响应消息体")
    @Data
    @Accessors(chain = true)
    public static class ResultMsg<T> {
        @Schema(description = "消息状态：0-成功，其他失败")
        private int code = 0;
        @Schema(description = "消息内容")
        private String msg = "SUCCESS";
        @Schema(description = "数据内容")
        private T data;

        public static <T> ResultMsg<T> ok(T data) {
            return new ResultMsg<T>().setData(data);
        }

        public static <T> ResultMsg<T> fail(String msg) {
            return new ResultMsg<T>().setCode(1).setMsg(msg);
        }
    }

    @Operation(summary = "模拟登录接口", description = "简单测试一下")
    @GetMapping("/fake/login")
    public Mono<ResultMsg<String>> fakeLogin(@Parameter(description = "用户名", example = "admin") @RequestParam String username,
                                             @Parameter(description = "密码", example = "admin") @RequestParam String password) {
        log.info("fakeLogin -> username={}, password={}", username, password);
        if (!Objects.equals(username, "admin") || !Objects.equals(password, "admin")) {
            return Mono.just(ResultMsg.fail("用户名或密码错误"));
        }
        // 返回聊天室ID，如果不返回ID则用户或密码错误
        return Mono.just(ResultMsg.ok("123"));
    }

    @Operation(summary = "聊天接口", description = "简单测试一下")
    @GetMapping("/chat")
    public Flux<ServerSentEvent<String>> chat(@Parameter(description = "会话ID", example = "1") @RequestParam int memoryId,
                                              @Parameter(description = "用户提问", example = "讲个笑话") @RequestParam String question) {
        log.info("chat -> memoryId={}, question={}", memoryId, question);
        return defaultChatService.chatWithJoke(memoryId, question)
                .map(chunk -> ServerSentEvent.<String>builder().data(chunk).build());
    }

    @Operation(summary = "协议内容提取接口", description = "简单测试一下")
    @PostMapping("/protocol/upload")
    public Mono<ResultMsg<String>> protocolUpload(@Parameter(description = "文件") @RequestPart("file") FilePart file) {
        log.info("protocolUpload -> file={}", file.filename());
        return Mono.fromCallable(() -> Files.createTempDirectory("__test__"))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(uploadPath -> {
                    Path filePath = uploadPath.resolve(file.filename());
                    return file.transferTo(filePath)
                            .then(Mono.fromCallable(() -> {
                                File tempFile = filePath.toFile();
                                return ResultMsg.ok(new Tika().parseToString(tempFile));
                            }))
                            .subscribeOn(Schedulers.boundedElastic());
                });
    }

    @Schema(description = "协议请求数据")
    @Data
    public static class ProtocolRequest {
        @Schema(description = "聊天室ID")
        private String chatId;
        @Schema(description = "协议内容")
        private String content;
    }

    @Operation(summary = "协议智能体调用接口", description = "简单测试一下")
    @PostMapping("/protocol/agent")
    public Mono<ResultMsg<String>> protocolAgent(@Parameter(description = "协议文档内容") @RequestBody ProtocolRequest body) throws Exception {
        String requestId = RequestIdUtil.get();
        log.info("protocolAgent -> chatId={}, content={}, ", body.getChatId(), body.getContent());
//        opinionWorkflowGraph.invokeStream(body.getContent(), body.getChatId(), requestId);
        opinionWorkflowGraph.invoke(body.getContent());
        RequestIdUtil.remove();
        // 返回任务ID
        return Mono.just(ResultMsg.ok(requestId));
    }
}
