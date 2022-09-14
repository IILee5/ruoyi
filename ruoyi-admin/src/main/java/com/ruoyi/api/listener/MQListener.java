package com.ruoyi.api.listener;

import com.ruoyi.common.constant.MQQueueConstants;
import com.ruoyi.framework.api.annotations.RedisListener;
import com.ruoyi.model.dto.Job;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author iilee
 */
@Slf4j
@Component
public class MQListener
{
    @RedisListener(queueName = MQQueueConstants.STREAM_KEY)
    public void test(Job job)
    {
        log.info("MQ 数据:{}", job);
    }
}
