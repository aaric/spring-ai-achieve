package com.github.aaric.salg.listener;

import com.github.aaric.salg.ws.OpinionWebSocketHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * RedisMessageListener
 *
 * @author Aaric
 * @version 0.20.0-SNAPSHOT
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RedisLogMessageListener implements MessageListener {

    private final StringRedisTemplate stringRedisTemplate;

    private final OpinionWebSocketHandler opinionWebSocketHandler;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        String body = new String(message.getBody());
//        log.debug("onMessage -> channel={}, body={}", channel, body);

        // 处理消息
        ListOperations<String, String> listOperations = stringRedisTemplate.opsForList();
        listOperations.rightPush(channel, body);

        // 发送WebSocket广播消息
        opinionWebSocketHandler.broadcastMessage(body);
    }
}
