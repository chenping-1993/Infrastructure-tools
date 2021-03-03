package com.example.cp.service.handler;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description: RabbitMq 接收队列 queueName2 消息监听类
 * @Author: chenping
 * @Date: 2021-03-02
 */
@Component
@RabbitListener(queues = "queueName2")//发送的队列名称
public class RabbitMqReceiver3Handler {

    @RabbitHandler
    public void receiveMessage(String message) {
        System.out.println("接收者3--接收到queueName2队列的消息为："+message);
    }
}
