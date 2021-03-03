package com.example.cp.service.handler;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description: RabbitMq 接收队列 topic.message的消息
 * @Author: chenping
 * @Date: 2021-03-02
 */
@Component
@RabbitListener(queues = "topic.message")//只接收topic.message队列名称的消息
public class TopicReceiver1Handler {

    @RabbitHandler
    public void process(String msg) {
        System.out.println("TopicReceiver1:" + msg);
    }
}
