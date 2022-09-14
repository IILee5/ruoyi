package com.ruoyi.framework.api.mq;

import com.ruoyi.framework.api.annotations.MQTopic;
import com.ruoyi.framework.api.delay.BaseTask;
import com.ruoyi.framework.api.delay.RedisDelayQueue;
import com.ruoyi.framework.api.queue.core.RedisMQSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 队列处理器
 *
 * @author huhaiqiang
 * @date 2022-08-30 10:00
 */
@Component
@Slf4j
public class MQHandler
{
    @Resource
    private RedisDelayQueue redisDelayQueue;
    @Resource
    private RedisMQSender redisQueue;

    /**
     * 延迟队列推送
     *
     * @param t   队列元素
     * @param <T> BaseTask子类
     */
    public <T extends BaseTask> void delayPush(T t)
    {
        MQTopic topic = t.getClass().getAnnotation(MQTopic.class);
        redisDelayQueue.addQueue(topic.queueKey(), t, topic.delayTime(), TimeUnit.SECONDS);
    }

    /**
     * 延迟队列删除
     *
     * @param t         队列元素
     * @param classType 队列元素类型
     * @param <T>       BaseTask子类
     * @return 被删除的队列
     */
    public <T extends BaseTask> List<T> delayDel(T t, Class<T> classType)
    {
        MQTopic topic = t.getClass().getAnnotation(MQTopic.class);
        return redisDelayQueue.removeQueueByIdentity(topic.queueKey(), t, classType);
    }

    /**
     * 队列推送
     *
     * @param t   队列元素
     */
    public <T> void push(T t)
    {
        MQTopic topic = t.getClass().getAnnotation(MQTopic.class);
        redisQueue.send(topic.queueKey(), t);
    }
}
