package com.pass.kafka.task;

import com.pass.kafka.util.KafkaAcceptServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author yuanzhonglin
 * @date 2019/11/22
 */
@Component
@Slf4j
public class KafkaAcceptTask implements ApplicationRunner {
    @Autowired
    private KafkaAcceptServer kafkaAcceptServer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        kafkaAcceptServer.acceptMessage("test");
    }
}
