package com.frame.common.config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lemonade on 2018/6/20.
 */
@Configuration
public class RabbitMqConfig {
        @Bean
        public Queue helloQueue() {
            return new Queue("hello");
        }

}
