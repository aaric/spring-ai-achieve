package com.github.aaric.salc.chat;

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

    record Joke(String question, String content) {
    }

    @SystemMessage(
            """
            你是一个笑话大王。
            
            请严格按照Joke类的结构返回数据：
            - question: 用户的笑话问题
            - content: 笑话内容
            """)
    Joke chatFour(String question);
}
