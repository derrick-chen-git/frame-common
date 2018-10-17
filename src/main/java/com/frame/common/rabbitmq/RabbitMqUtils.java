package com.frame.common.rabbitmq;

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
/*    @Autowired
    private ConnectionFactory connectionFactory;


    public void convertAndSend(String exchange,String routingKey,String msg) throws IOException, TimeoutException {
     MessageSender messageSender = new MQAccessBuilder(connectionFactory).buildMessageSender(exchange,routingKey);
     messageSender.send(msg);
    }*/
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String exchange,String routingKey,String msg){
        log.info("发送++++++++++++++++++++++++++++++++++++++++++++"+msg);
        rabbitTemplate.convertAndSend(exchange,routingKey,msg);
    }


}
