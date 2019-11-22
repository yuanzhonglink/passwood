package com.pass.kafka.task;

import com.pass.kafka.util.KafkaSendServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * kafka消息发布者
 *
 * @author yuanzhonglin
 * @date 2019/11/21
 */
@Order(value = 1)
@Slf4j
@Component
public class KafkaProviderTask implements ApplicationRunner {
    @Autowired
    private KafkaSendServer kafkaSendServer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        int i = 0;
        while (true) {
            kafkaSendServer.sendMsg("test", "hello world!["+ ++i +"]");
            Thread.sleep(1000 * 20);
        }
    }
}
