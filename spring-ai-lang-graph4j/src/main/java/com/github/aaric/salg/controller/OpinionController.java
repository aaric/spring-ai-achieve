package com.github.aaric.salg.controller;

import com.github.aaric.salg.demo.opinion.graph.OpinionWorkflowGraph;
import com.github.aaric.salg.util.RequestIdUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 舆情模块API控制器
 *
 * @author Aaric
 * @version 0.22.0-SNAPSHOT
 */
@Tag(name = "舆情模块API", description = "简单测试一下")
@RequestMapping("/api/opinion")
@RestController
@Slf4j
@RequiredArgsConstructor
public class OpinionController {

    private final OpinionWorkflowGraph opinionWorkflowGraph;

    @Operation(summary = "执行工作流", description = "简单测试一下")
    @GetMapping("/workflow/invoke")
    public Mono<Map<String, Object>> workflowInvoke(@Parameter(description = "用户提问", example = "无聊") @RequestParam String question) throws Exception {
        //HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestId = RequestIdUtil.get();
        log.info("workflowInvoke -> question={}, requestId={}", question, requestId);
        Mono<Map<String, Object>> result = Mono.just(opinionWorkflowGraph.invoke(question).data());
        RequestIdUtil.remove();
        return result;
    }
}
