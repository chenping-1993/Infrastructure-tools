package com.example.cp.service.handler;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description: RabbitMq 接收队列 queueName1 消息监听类
 * @Author: chenping
 * @Date: 2021-03-02
 */
@Component
@RabbitListener(queues = "queueName1")//发送的队列名称
public class RabbitMqReceiver1Handler {

    @RabbitHandler
    public void receiveMessage(String message) {
        System.out.println("接收者1--接收到queueName1队列的消息为："+message);
    }
}
