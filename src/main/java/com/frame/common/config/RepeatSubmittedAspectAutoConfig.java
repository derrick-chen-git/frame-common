package com.frame.common.config;

import com.frame.common.aop.RepeatSubmittedAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 防重复提交拦截
 */
@Configuration
@ConditionalOnClass(RepeatSubmittedAspect.class)
@ConditionalOnProperty(prefix = "frame.repeat.submit", name = "enable", havingValue = "true")
public class RepeatSubmittedAspectAutoConfig {
    
    @Bean
    @Order(-5)
    @ConditionalOnMissingBean
    public RepeatSubmittedAspect repeatSubmittedAspect() {
        return new RepeatSubmittedAspect();
    }
    
}
