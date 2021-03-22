package com.pass.server.service;

import com.pass.server.config.RabbitConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息确认机制
 *
 * @author yuanzhonglin
 * @date 2021/3/22
 */
@Component
public class MsgConfirmProducer implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send2FanoutTestQueue(String massage){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration("20000"); // 设置过期时间，单位：毫秒
        byte[] msgBytes = massage.getBytes();
        Message message = new Message(msgBytes, messageProperties);
        rabbitTemplate.convertAndSend(RabbitConfig.TEST_FANOUT_EXCHANGE,
                "", message);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        System.out.println(correlationData.getId());
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {

    }
}
