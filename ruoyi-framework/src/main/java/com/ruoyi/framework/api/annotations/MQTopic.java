package com.ruoyi.framework.api.annotations;

import java.lang.annotation.*;

/**
 * 消息队列注解
 *
 * @author iilee
 * @date 2022-09-02 10:56
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MQTopic
{
    /**
     * 队列名称
     */
    String queueKey();

    /**
     * 队列延迟时间，默认60秒
     */
    long delayTime() default 60L;
}
