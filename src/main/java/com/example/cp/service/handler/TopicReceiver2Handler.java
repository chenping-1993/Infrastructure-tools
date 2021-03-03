package com.example.cp.service.handler;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description: RabbitMq 接收 以topic.开头的队列的消息
 * @Author: chenping
 * @Date: 2021-03-02
 */
@Component
@RabbitListener(queues = "topic.messages")//只要以topic.开头的队列都能收到消息
public class TopicReceiver2Handler {

    @RabbitHandler
    public void process(String msg) {
        System.out.println("TopicReceiver2:" + msg);
    }
}
