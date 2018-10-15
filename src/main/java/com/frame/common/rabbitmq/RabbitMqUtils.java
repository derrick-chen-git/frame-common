package com.frame.common.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
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
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void initRabbitMqQueue(String exchangeName,String queueName, String type, String routingKey,boolean durable, boolean autoDelete){
        Queue queue = new Queue(queueName);
        rabbitAdmin.declareQueue(new Queue(queueName));
        AbstractExchange exchange = null;
       if(ExchangeTypes.TOPIC.equals(type)) {
           exchange = new TopicExchange(exchangeName, durable, autoDelete);
       }else if(ExchangeTypes.DIRECT.equals(type)) {
           exchange = new DirectExchange(exchangeName, durable, autoDelete);
       }else if(ExchangeTypes.FANOUT.equals(type)){
           exchange = new FanoutExchange(exchangeName,durable,autoDelete);
       }else {
           exchange = new HeadersExchange(exchangeName,durable,autoDelete);
       }
        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareBinding(this.getBinding(exchange,routingKey,queue));
    }
    private Binding getBinding(AbstractExchange defaultExchange, String Key, Queue queue) {
        if(defaultExchange instanceof TopicExchange){
            return BindingBuilder.bind(queue).to((TopicExchange)defaultExchange).with(Key);
        }
        if(defaultExchange instanceof FanoutExchange){
            return BindingBuilder.bind(queue).to((FanoutExchange)defaultExchange);
        }
        if(defaultExchange instanceof DirectExchange){
            return BindingBuilder.bind(queue).to((DirectExchange)defaultExchange).with(Key);
        }
        return null;
    }
    public void convertAndSend(String exchange,String routingKey,Object obj){
        log.info(" rabbitmq send msg exchange:{}-->routingKey:{}",exchange,routingKey);
        rabbitTemplate.convertAndSend(exchange,routingKey,obj);
    }

}
