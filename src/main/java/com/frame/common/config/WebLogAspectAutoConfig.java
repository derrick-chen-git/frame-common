package com.frame.common.config;

import com.frame.common.aop.WebLogAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 实现Web层的请求参数，返回结果，耗时时间打印
 */
@Configuration
@ConditionalOnClass(WebLogAspect.class)
public class WebLogAspectAutoConfig {
    
    @Bean
    @Order(-5)
    @ConditionalOnMissingBean
    @ConditionalOnWebApplication
    public WebLogAspect webLogAspect() {
        return new WebLogAspect();
    }
    
}
