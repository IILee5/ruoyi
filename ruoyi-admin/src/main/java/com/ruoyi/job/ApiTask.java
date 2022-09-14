package com.ruoyi.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@Component("apiTask")
@Slf4j
public class ApiTask
{
    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        log.info("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i);
    }

    public void ryParams(String params)
    {
        log.info("执行有参方法：" + params);
    }

    public void ryNoParams()
    {
        log.info("执行无参方法");
    }
}
