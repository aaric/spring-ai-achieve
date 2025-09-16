package com.github.aaric.salc.chat;

import com.github.aaric.salc.guardrail.CustomInputGuardrail;
import dev.langchain4j.model.output.structured.Description;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.guardrail.InputGuardrails;
import reactor.core.publisher.Flux;

/**
 * 测试对话 Service接口
 *
 * @author Aaric
 * @version 0.17.0-SNAPSHOT
 */
//@AiService
@InputGuardrails({CustomInputGuardrail.class})
public interface TestChatService {

    @SystemMessage("你是一个笑话大王。")
    String chatOne(String question);

    @SystemMessage(fromResource = "chat/system-prompt.txt")
    String chatTwo(String question);

    @SystemMessage(fromResource = "chat/system-prompt.txt")
    String chatThree(@MemoryId int memoryId, @UserMessage String question);

    record Joke(@Description("用户提问") String question,
                @Description("笑话内容") String content) {
    }

    /*@SystemMessage("""
            你是一个笑话大王。

            请严格按照Joke类的结构返回数据：
            - question: 用户提问
            - content: 返回的笑话内容
            """)*/
    @SystemMessage(fromResource = "chat/system-prompt.txt")
    Joke chatFour(String question);

    @SystemMessage("你是一个网络搜索专家。")
    String chatFive(String question);

    @SystemMessage(fromResource = "chat/system-prompt.txt")
    Flux<String> chatSix(@MemoryId int memoryId, @UserMessage String question);

    @SystemMessage(fromResource = "chat/system-prompt.txt")
    @UserMessage("讲个笑话，类型为{{jokeType}}")
    String chatSeven(@V("jokeType") String jokeType);
}
