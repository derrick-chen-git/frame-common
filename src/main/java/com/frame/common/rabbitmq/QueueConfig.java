package com.frame.common.rabbitmq;
import java.util.List;

public class QueueConfig {
    public List<MyQueue> queues ;

    public List<MyQueue> getQueues() {
        return queues;
    }

    public void setQueues(List<MyQueue> queues) {
        this.queues = queues;
    }


}
