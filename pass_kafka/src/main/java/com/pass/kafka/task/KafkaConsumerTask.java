package com.pass.kafka.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Kafka消息消费
 *
 * @author yuanzhonglin
 * @date 2019/11/21
 */
@Component
@Slf4j
@Order(value = 2)
public class KafkaConsumerTask implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }

}
