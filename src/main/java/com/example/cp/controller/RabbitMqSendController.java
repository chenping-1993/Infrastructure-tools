package com.example.cp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Description: RabbitMq 发送消息控制类
 * @Author: chenping
 * @Date: 2021-03-02
 */
@Api(tags = "RabbitMq 测试")
@RestController
public class RabbitMqSendController {

    @Autowired
    AmqpTemplate amqpTemplate;

    /** 
     * @Description:  一对一发送，queueName1的一个消息只能被一个receiver(消费者)接收
     * @param:  
     * @return: java.lang.String 
     * @Author: chenping
     * @Date: 2021/3/2 14:26
     */
    @ApiOperation(value = "一对一发送" ,  notes="一个消息 一个消息监听接收者")
    @GetMapping("/rabbitmq/send")
    public String oneToOneSend() {
        String message = "server message";
        amqpTemplate.convertAndSend("queueName1",message);
        return message;
    }

    /**
     * @Description:  一对多发送消息--一个队列发送多条消息给多个消费者，多个消费者均分消息
     *          queueName1的 一个消息 只能被一个receiver(消费者)接收
     * @param:
     * @return: java.lang.String
     * @Author: chenping
     * @Date: 2021/3/2 14:30
     */
    @ApiOperation(value = "一对多发送消息" ,  notes="一个队列 多个消息监听者")
    @GetMapping("/rabbitmq/oneToManySend")
    public String oneToManySend() {
        StringBuilder times=new StringBuilder();
        for(int i=0;i<10;i++){
            long time=System.nanoTime();
            amqpTemplate.convertAndSend("queueName1","第"+i+"次发送的时间："+time);
            times.append(time+"<br>");
        }
        return times.toString();
    }

    /** 
     * @Description:  多对多发送消息--多个队列发送多条消息给多个消费者
     * @param:  
     * @return: java.lang.String 
     * @Author: chenping
     * @Date: 2021/3/2 14:50
     */
    @ApiOperation(value = "多对多发送消息" ,  notes="多个队列 多个消息接收者")
    @GetMapping("/rabbitmq/manyToManySend")
    public String manyToManySend() {
        StringBuilder times=new StringBuilder();
        for(int i=0;i<10;i++){
            long time=System.nanoTime();
            amqpTemplate.convertAndSend("queueName1","第"+i+"次发送的时间："+time);
            amqpTemplate.convertAndSend("queueName2","第"+i+"次发送的时间："+time);
            times.append(time+"<br>");
        }
        return times.toString();
    }


    /**
     * @Description:  发送到其他的系统，其他的系统除了有接收消息的监听，也要有config配置队列的文件、yml 配置
     * @param:
     * @return: java.lang.String
     * @Author: chenping
     * @Date: 2021/3/3 10:42
     */
    @ApiOperation(value = "发送到其他的系统-服务端和客户端两个系统" ,  notes="发送到其他的系统-服务端和客户端两个系统")
    @GetMapping("/rabbitmq/sendToClient")
    public String sendToClient() {
        String message = "server message sendToClient";
        for (int i = 0; i < 10000; i++) {
            amqpTemplate.convertAndSend("queueName3",message+": "+i);

        }
        return message;
    }

    /**
     * @Description:  发送消息 模拟消息重复消费
     *      消息重复消费情景：消息生产者已把消息发送到mq，消息消费者在消息消费的过程中突然因为网络原因或者其他原因导致消息消费中断
     *      消费者消费成功后，在给MQ确认的时候出现了网络波动，MQ没有接收到确认，
     *      为了保证消息被消费，MQ就会继续给消费者投递之前的消息。这时候消费者就接收到了两条一样的消息
     * @param:
     * @return: java.lang.String
     * @Author: chenping
     * @Date: 2021/3/5 17:25
     */
    @GetMapping("/rabbitmq/sendMsgNoRepeat")
    public String sendMsgNoRepeat() {
        String message = "server message sendMsgNoRepeat";
        for (int i = 0; i <10000 ; i++) {
            Message msg = MessageBuilder.withBody((message+"--"+i).getBytes()).setMessageId(UUID.randomUUID()+"").build();
            amqpTemplate.convertAndSend("queueName4",msg);
        }
        return message;
    }
//    ================================================================================================================
//                                              topic消息

    /**
     * topic.message 队列发送的消息 能被监听 topic.message 和监听 topic.messages的监听接收者 接收到消息
     * topic.messages 队列发送的消息 只能被监听 topic.messages的监听接收者 接收到消息
     */
    @ApiOperation(value = "topic.message 监听者监听以topic.开头的都能收到消息")
    @GetMapping("/topicSend1")
    public String  topicSend1() {
        String context = "my topic 1";
        System.out.println("发送者说 : " + context);
        this.amqpTemplate.convertAndSend("exchange", "topic.message", context);
        return context;
    }
    @ApiOperation(value = "topic.message 监听者只监听topic.messages的消息")
    @GetMapping("/topicSend2")
    public String topicSend2() {
        String context = "my topic 2";
        System.out.println("发送者说 : " + context);
        this.amqpTemplate.convertAndSend("exchange", "topic.messages", context);
        return  context;
    }

}
