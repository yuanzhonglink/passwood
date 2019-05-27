package com.pass.api.rabbitMQ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ
 *
 * @author yuanzhonglin
 * @date 2019/5/27
 */
@Component
@Slf4j
public class RabbitMQApi {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(Object message) {
        try {
            this.amqpTemplate.convertAndSend("", "", message);
        } catch (AmqpException e) {
            e.printStackTrace();
            log.error("Send Resource Event Error{}");
        }
    }
}
