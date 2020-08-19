package com.pass.common;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author yuanzhonglin
 * @date 2020/8/19
 * @Description:
 */
@Component
public class RabbitRec implements MessageListener {

    @Override
    @RabbitListener(queues = "resource.alarm.queue")
    public void onMessage(Message message) {
        String content = new String(message.getBody());
        System.out.println(content);
        System.out.println("----------------------");
    }
}
