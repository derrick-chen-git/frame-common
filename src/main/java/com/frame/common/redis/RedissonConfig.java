package com.frame.common.redis;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix="redisson")
@Data
public class RedissonConfig {
    private int scanInterval;
    private String[] nodeAddresses;
    private String loadBalancer;
    private String readMode;
    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useClusterServers().addNodeAddress(nodeAddresses)
                .setScanInterval(scanInterval).setReadMode(ReadMode.valueOf(readMode));
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
