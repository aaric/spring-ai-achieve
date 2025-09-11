package com.github.aaric.salc.chat;

import dev.langchain4j.service.SystemMessage;

/**
 * 测试对话 Service接口
 *
 * @author Aaric
 * @version 0.17.0-SNAPSHOT
 */
public interface TestChatService {

    @SystemMessage("你是一个笑话大王。")
    String chatOne(String question);

    @SystemMessage(fromResource = "chat/system-prompt.txt")
    String chatTwo(String question);
}
