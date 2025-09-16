package com.github.aaric.salg.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * OpinionProcessService
 *
 * @author Aaric
 * @version 0.19.0-SNAPSHOT
 */
public interface OpinionProcessService {

    @SystemMessage("你是一个舆情处理专家，你的任务是将用户的问题派发到不同的部，如：医院、学校、银行。仅输出最终的结果，不需要解析内容。")
    @UserMessage("用户提问：{{question}}")
    String chat(@V("question") String question);
}
