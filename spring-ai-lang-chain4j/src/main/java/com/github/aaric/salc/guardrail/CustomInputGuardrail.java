package com.github.aaric.salc.guardrail;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.guardrail.InputGuardrail;
import dev.langchain4j.guardrail.InputGuardrailResult;

/**
 * 自定义输入关键字过滤器
 *
 * @author Aaric
 * @version 0.17.0-SNAPSHOT
 */
public class CustomInputGuardrail implements InputGuardrail {

    @Override
    public InputGuardrailResult validate(UserMessage userMessage) {
        if (userMessage.singleText().contains("死亡")) {
            return failure("请勿使用死亡关键字！");
        }
        return success();
    }
}
