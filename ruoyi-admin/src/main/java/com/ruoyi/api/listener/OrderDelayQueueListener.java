package com.ruoyi.api.listener;

import com.alibaba.fastjson.JSON;
import com.ruoyi.framework.api.delay.DelayTaskListener;
import com.ruoyi.model.dto.OrderDelayQueueDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 延迟队列测试一
 *
 * @author iilee
 */
@Slf4j
@Component
public class OrderDelayQueueListener implements DelayTaskListener<OrderDelayQueueDTO>
{

    @Override
    public void invoke(OrderDelayQueueDTO orderDelayQueueDTO)
    {
        // 业务逻辑处理
        log.info("订单超时逻辑：{}", JSON.toJSONString(orderDelayQueueDTO));
    }
}
