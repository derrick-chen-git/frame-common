package com.frame.common.config;


import com.frame.common.aop.SignAuthorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 权限验签配置
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(SignAuthProperties.class)
@ConditionalOnProperty(prefix = SignAuthProperties.PREFIX, name = "enable", havingValue = "true")
public class SignAuthAutoConfigure {
    
    @Autowired
    private SignAuthProperties signAuthProperties;
    
    @Bean
    @Order(-4)
    @ConditionalOnMissingBean
    @ConditionalOnWebApplication
    public SignAuthorization signAuthorization() {
        log.debug("EnableAutoConfig SignAuthAutoConfigure success");
        return new SignAuthorization(signAuthProperties);
    }
    
}
