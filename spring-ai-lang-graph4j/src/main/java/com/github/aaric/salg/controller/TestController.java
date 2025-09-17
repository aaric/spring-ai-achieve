package com.github.aaric.salg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.aaric.salg.graph.OpinionWorkflowGraph;
import com.github.aaric.salg.log.LlmLog;
import com.github.aaric.salg.util.MDCRequestIdUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 测试模块API控制器
 *
 * @author Aaric
 * @version 0.20.0-SNAPSHOT
 */
@Tag(name = "测试模块API", description = "测试学习示例相关操作")
@RequestMapping("/api/test")
@RestController
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final ObjectMapper objectMapper;

    private final OpinionWorkflowGraph opinionWorkflowGraph;

    private final StringRedisTemplate stringRedisTemplate;

    private ListOperations<String, String> listOperations;

    @PostConstruct
    public void init() {
        listOperations = stringRedisTemplate.opsForList();
    }

    @Operation(summary = "测试工作流", description = "简单测试一下")
    @GetMapping("/workflow/opinion")
    public Mono<Map<String, Object>> workflowOpinion(@Parameter(description = "用户提问", example = "无聊") @RequestParam String question) throws Exception {
        //HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestId = MDCRequestIdUtil.getRequestId();
        log.info("workflowOpinion -> question={}, requestId={}", question, requestId);
        Mono<Map<String, Object>> result = Mono.just(opinionWorkflowGraph.invoke(question).data());
        MDCRequestIdUtil.removeRequestId();
        return result;
    }

    @Operation(summary = "查询日志列表", description = "简单测试一下")
    @GetMapping("/log/list")
    public Mono<List<LlmLog>> logList() throws Exception {
        List<LlmLog> llmLogList = new ArrayList<>();
        List<String> llmLogJsonList = listOperations.range(LlmLog.LLM_LOG_KEY, 0, -1);
        if (!CollectionUtils.isEmpty(llmLogJsonList)) {
            for (String llmLogJson : llmLogJsonList) {
                llmLogList.add(objectMapper.readValue(llmLogJson, LlmLog.class));
            }
        }
        return Mono.just(llmLogList);
    }
}
