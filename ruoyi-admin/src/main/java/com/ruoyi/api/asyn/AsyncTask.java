package com.ruoyi.api.asyn;


import com.ruoyi.common.utils.Threads;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步任务
 *
 * @author iilee
 */
@Component
@Slf4j
public class AsyncTask
{
    @Async
    public void doTask1() {
        long t1 = System.currentTimeMillis();
        Threads.sleep(2000);
        long t2 = System.currentTimeMillis();
        log.info("task1 cost {} ms" , t2-t1);
    }
}
