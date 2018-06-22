package com.dev.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by lemonade on 2018/6/20.
 */
@Slf4j
@Component
public class RabbitMqUtils {
        @Autowired
        private AmqpTemplate rabbitTemplate;


        public void sendMsg(String topic, Object msg) {
            log.info("RabbitMqUtils senMsg start topic:{},msg:{}",topic,msg);
            try {
                this.rabbitTemplate.convertAndSend(topic, msg);
            }catch(Exception e){
                log.error("RabbitMqUtils senMsg error topic:{},error msg:{}",topic,e.getMessage());
            }
        }


}
