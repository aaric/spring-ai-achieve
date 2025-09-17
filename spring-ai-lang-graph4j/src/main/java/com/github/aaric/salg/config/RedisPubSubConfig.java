package com.github.aaric.salg.config;

import com.github.aaric.salg.listener.RedisLogMessageListener;
import com.github.aaric.salg.log.LlmLog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * RedisPubSubConfig
 *
 * @author Aaric
 * @version 0.20.0-SNAPSHOT
 */
@Configuration
public class RedisPubSubConfig {

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory,
                                                                       RedisLogMessageListener redisLogMessageListener) {
        // 初始化监听容器
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        // 订阅频道
        container.addMessageListener(redisLogMessageListener, new ChannelTopic(LlmLog.LLM_LOG_KEY));
        return container;
    }
}
