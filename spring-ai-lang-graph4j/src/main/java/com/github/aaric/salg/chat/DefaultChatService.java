package com.github.aaric.salg.chat;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;

/**
 * DefaultChatService
 *
 * @author Aaric
 * @version 0.22.0-SNAPSHOT
 */
public interface DefaultChatService {

    @SystemMessage("你是一个笑话大王。")
    Flux<String> chatWithJoke(@MemoryId int memoryId, @UserMessage String question);
}
