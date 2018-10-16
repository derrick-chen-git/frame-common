package com.frame.common.rabbitmq;

import com.frame.common.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * Created by lemonade on 2018/6/20.
 */
@Slf4j
@Component
public class RabbitMqUtils {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void convertAndSend(String exchange,String routingKey,String msg){

        log.info(" rabbitmq send msg exchange:{}-->routingKey:{}-->msg:{}",exchange,routingKey,msg);
        rabbitTemplate.convertAndSend(exchange,routingKey, msg);
    }


}
