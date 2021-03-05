package com.example.cp.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 队列配置，队列的名称，发送者和接受者的名称必须一致，否则接收不到消息
 * @Author: chenping
 * @Date: 2021-03-02
 */
@Configuration
public class RabbitMqConfig {
    //只接一个topic
    final static String message = "topic.message";
    //接收多个topic
    final static String messages = "topic.messages";


    /** 
     * @Description: 队列1 一个队列的消息只能被一个消费者收到
     * @param:  
     * @return: org.springframework.amqp.core.Queue 
     * @Author: chenping
     * @Date: 2021/3/2 14:24
     */
    @Bean
    public Queue Queue1() {
        return new Queue("queueName1");
    }

    /**
     * @Description:  队列2
     * @param:
     * @return: org.springframework.amqp.core.Queue
     * @Author: chenping
     * @Date: 2021/3/2 14:51
     */
    @Bean
    public Queue Queue2() {
        return new Queue("queueName2");
    }

    @Bean
    public Queue Queue3() {
        return new Queue("queueName3");
    }

    @Bean
    public Queue Queue4() {
        return new Queue("queueName4");
    }

//    ====================================================================================================================
//    Topic Exchange是RabbitMQ中最灵活的一种方式，它能够根据routing_key自由的绑定不同的队列，可以适用绝大部分的项目需求

    @Bean
    public Queue queueMessage() {
        return new Queue(message);
    }

    @Bean
    public Queue queueMessages() {
        return new Queue(messages);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("exchange");
    }

    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }

    /**
     * @Description:  匹配 topic. 开头的队列名称
     * @param: queueMessages
     * @param: exchange
     * @return: org.springframework.amqp.core.Binding
     * @Author: chenping
     * @Date: 2021/3/2 15:16
     */
    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        //这里的#表示零个或多个词。
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }
}
