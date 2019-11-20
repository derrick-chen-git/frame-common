package com.frame.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @desc 异步线程配置
 *
 * @author derrick
 * @version v1.0
 */
@Data
@Configuration
@ConfigurationProperties(AsyncConfigProperties.PREFIX)
public class AsyncConfigProperties {

    public static final String PREFIX = "frame.async.thread";
    /** 是否启用 **/
    private String enable = "false";
    /** 当前线程数 **/
    private int corePoolSize = 5;
    /** 最大线程数 **/
    private int maxPoolSize = 5;
    /** 线程池所使用的缓冲队列 **/
    private int queueCapacity = 100000;

}
