package com.ruoyi.framework.api.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.framework.api.queue.core.RedisListenerAnnotationScanPostProcesser;
import com.ruoyi.framework.api.queue.core.RedisMQSender;
import com.ruoyi.framework.api.queue.core.RedisMessageQueueRegister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import java.time.Duration;

/**
 * 消息队列注册类
 *
 * @author iilee
 */

@Configuration
public class RedisMQListenerAutoConfig
{

    @Bean
    public RedisListenerAnnotationScanPostProcesser redisListenerAnnotationScanPostProcesser()
    {
        return new RedisListenerAnnotationScanPostProcesser();
    }

    @Bean
    public StringRedisTemplate redisMQTemplate(RedisConnectionFactory redisConnectionFactory)
    {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        return stringRedisTemplate;
    }

    @Bean
    public RedisMessageQueueRegister redisMessageQueueRegister()
    {
        return new RedisMessageQueueRegister();
    }


    @Bean
    public RedisMQSender redisMQSender(StringRedisTemplate redisMQTemplate, ObjectMapper objectMapper)
    {
        return new RedisMQSender(redisMQTemplate, objectMapper);
    }

    @Bean
    public StreamMessageListenerContainer<String, ObjectRecord<String, String>> streamMessageListenerContainer(RedisConnectionFactory redisConnectionFactory)
    {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, String>> options = StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(2L))
                .batchSize(10)
                .targetType(String.class)
                .executor(SpringUtils.getBean("scheduledExecutorService"))
                .build();

        StreamMessageListenerContainer<String, ObjectRecord<String, String>> stringMapRecordStreamMessageListenerContainer = StreamMessageListenerContainer.create(redisConnectionFactory, options);
        stringMapRecordStreamMessageListenerContainer.start();
        return stringMapRecordStreamMessageListenerContainer;
    }
}
