package com.ruoyi.api.listener;

import com.ruoyi.common.constant.MQQueueConstants;
import com.ruoyi.framework.api.delay.RedisDelayEngine;
import com.ruoyi.framework.api.delay.RedisDelayQueue;
import com.ruoyi.model.dto.OrderDelayQueueDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 延迟队列引擎配置
 *
 * @author iilee
 */
@Slf4j
@Configuration
public class DelayQueueEngine
{
    @Resource
    private RedisDelayQueue redisDelayQueue;
    @Resource
    private OrderDelayQueueListener orderDelayQueueListener;

    @Bean(value = "taskStartOrderDelayEngine", destroyMethod = "destroy")
    public RedisDelayEngine<OrderDelayQueueDTO> taskStartOrderDelayEngine()
    {
        log.info("订单延时任务引擎初始化");
        RedisDelayEngine<OrderDelayQueueDTO> redisDelayEngine = new RedisDelayEngine<>(redisDelayQueue, MQQueueConstants.DELAY_ORDER, orderDelayQueueListener, OrderDelayQueueDTO.class);
        // 异常重试次数
        redisDelayEngine.setTryTimes(1);
        return redisDelayEngine;
    }
}
