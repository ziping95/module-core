package com.wzp.module.core.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    /** 消息交换机的名字*/
    public static final String DIRECT_EXCHANGE = "direct-exchange";
    public static final String FANOUT_EXCHANGE = "fanout-exchange";
    public static final String TOPIC_EXCHANGE = "topic-exchange";

    /** 队列名称*/
    public static final String DIRECT_QUEUE_1 = "direct-queue-1";
    public static final String DIRECT_QUEUE_2 = "direct-queue-2";
    public static final String FANOUT_QUEUE_1 = "fanout-queue-1";
    public static final String FANOUT_QUEUE_2 = "fanout-queue-2";
    public static final String TOPIC_QUEUE_1 = "topic-queue-1";
    public static final String TOPIC_QUEUE_2 = "topic-queue-2";
    public static final String WORK_QUEUE_1 = "work-queue-1";

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        /**
         * 推送消息存在四种情况：
         * 1、消息推送到server，但是在server里找不到交换机 ----> 触发ConfirmCallback回调
         * 2、消息推送到server，找到交换机了，但是没找到队列 ----> 触发ConfirmCallback和ReturnCallback 但ConfirmCallback返回true
         * 3、消息推送到sever，交换机和队列啥都没找到 ----> 触发ConfirmCallback回调
         * 4、消息推送成功 触发ConfirmCallback回调 ----> 返回true
         */
        // 消息确认, yml需要配置 publisher-confirms: true
        // 以下是生产者消息发送消息确认
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("ConfirmCallback:     "+"相关数据："+correlationData);
                System.out.println("ConfirmCallback:     "+"确认情况："+ack);
                System.out.println("ConfirmCallback:     "+"原因："+cause);
            }
        });

        // 消息返回, yml需要配置 publisher-returns: true
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("ReturnCallback:     "+"消息："+message);
                System.out.println("ReturnCallback:     "+"回应码："+replyCode);
                System.out.println("ReturnCallback:     "+"回应信息："+replyText);
                System.out.println("ReturnCallback:     "+"交换机："+exchange);
                System.out.println("ReturnCallback:     "+"路由键："+routingKey);
            }
        });

        return rabbitTemplate;
    }

}
