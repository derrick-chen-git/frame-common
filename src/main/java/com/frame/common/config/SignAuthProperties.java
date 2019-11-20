package com.frame.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(SignAuthProperties.PREFIX)
public class SignAuthProperties {
    
    public static final String PREFIX = "frame.sign";
    
    private String enable;
    /**
     * 第三方公钥
     */
    private String thirdPublicKey;
    /**
     * welab私钥
     */
    private String privateKey;
    
}
