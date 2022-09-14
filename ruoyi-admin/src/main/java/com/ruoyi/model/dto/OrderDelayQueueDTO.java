package com.ruoyi.model.dto;

import com.ruoyi.common.constant.MQQueueConstants;
import com.ruoyi.framework.api.annotations.MQTopic;
import com.ruoyi.framework.api.delay.BaseTask;
import lombok.*;

/**
 * 订单延迟队列DTO
 *
 * @author iilee
 */
@MQTopic(queueKey = MQQueueConstants.DELAY_ORDER, delayTime = 600L)
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDelayQueueDTO extends BaseTask
{
    private String orderId;

    @Override
    public String taskIdentity()
    {
        return orderId;
    }
}
