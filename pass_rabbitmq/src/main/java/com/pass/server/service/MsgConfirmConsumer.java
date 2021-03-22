package com.pass.server.service;

import com.pass.server.config.RabbitConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;


@Component
@Slf4j
public class MsgConfirmConsumer {

    /**
     * 消息确认机制改为手动确认
     */
    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue(value = RabbitConfig.FANOUT_QUEUE_NAME, durable = "true"), exchange = @Exchange(value = RabbitConfig.TEST_FANOUT_EXCHANGE, type = "fanout"))
    })
    @RabbitHandler
    public void processFanoutMsg(Message massage, Channel channel) throws Exception {
        try {
            channel.basicNack(massage.getMessageProperties().getDeliveryTag(), false, false);
            String msg = new String(massage.getBody(), StandardCharsets.UTF_8);
            log.info("received Fanout message : " + msg);
        } catch (Exception e) {
            channel.basicNack(massage.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}
