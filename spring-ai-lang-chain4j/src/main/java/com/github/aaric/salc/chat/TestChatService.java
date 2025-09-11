package com.github.aaric.salc.chat;

import dev.langchain4j.model.output.structured.Description;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

/**
 * 测试对话 Service接口
 *
 * @author Aaric
 * @version 0.17.0-SNAPSHOT
 */
//@AiService
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
}
