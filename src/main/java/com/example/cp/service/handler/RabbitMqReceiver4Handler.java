package com.example.cp.service.handler;

import com.example.cp.common.tool.RedisUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * @Description: RabbitMq 接收队列 queueName4 消息,模拟消息重复消费解决方案
 * @Author: chenping
 * @Date: 2021-03-05
 */
@Component
public class RabbitMqReceiver4Handler {

    @Autowired
    RedisUtil redisUtil;

    /**
     * @Description: 发送消息 模拟消息重复消费
     *       消息重复消费情景：消息生产者已把消息发送到mq，消息消费者在消息消费的过程中突然因为网络原因或者其他原因导致消息消费中断
     *       消费者消费成功后，在给MQ确认的时候出现了网络波动，MQ没有接收到确认，
     *       为了保证消息被消费，MQ就会继续给消费者投递之前的消息。这时候消费者就接收到了两条一样的消息
     * @param: message
     * @return: void
     * @Author: chenping
     * @Date: 2021/3/5 17:56
     */
    @RabbitListener(queues = "queueName4")//发送的队列名称     @RabbitListener注解到类和方法都可以
    @RabbitHandler
    public void receiveMessage(Message message) throws UnsupportedEncodingException {
        String messageId = message.getMessageProperties().getMessageId();
        String msg = new String(message.getBody(),"utf-8");

        String messageRedisValue = redisUtil.get("messageId","");
        if (messageRedisValue.equals(messageId)) {
            return;
        }
        System.out.println("消息："+msg+", id:"+messageId);

        redisUtil.set("messageId",messageId);
    }
}
