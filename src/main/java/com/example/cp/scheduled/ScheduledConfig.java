package com.example.cp.scheduled;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * @Description: 定时任务线程池配置
 * @Author: chenping
 * @Date: 2020-05-21
 */
@Configuration
public class ScheduledConfig {

    @Bean
    public ScheduledThreadPoolExecutor scheduledExecutorService() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10,
                new ThreadFactoryBuilder().setNameFormat("scheduled-executor" + "-%d").setDaemon(true).build());
        return executor;
    }

}
