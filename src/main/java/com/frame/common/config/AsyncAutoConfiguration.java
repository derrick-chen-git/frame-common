package com.frame.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @desc 自定义异步线程
 * @version v1.0
 */
@Slf4j
@EnableAsync
@EnableConfigurationProperties(AsyncConfigProperties.class)
@ConditionalOnProperty(prefix = AsyncConfigProperties.PREFIX , name = "enable", havingValue = "true")
public class AsyncAutoConfiguration {

    @Autowired
    private AsyncConfigProperties asyncConfigProperties;

    /**
     * 获取异步线程池执行对象
     * @return
     */
    @Bean("taskExecutor")
    public Executor getAsyncExecutor() {

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(asyncConfigProperties.getCorePoolSize());
        taskExecutor.setMaxPoolSize(asyncConfigProperties.getMaxPoolSize());
        taskExecutor.setQueueCapacity(asyncConfigProperties.getQueueCapacity());

        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setThreadNamePrefix("taskExecutor-");
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(60);

        return taskExecutor;
    }

}