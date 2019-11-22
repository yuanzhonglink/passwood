package com.pass.kafka.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Properties;

/**
 *
 * 功能描述: kafka订阅消息
 *
 * @auther: yuanzhonglin
 * @date: 2018/12/14 13:17
 */
@Slf4j
@Component
public class KafkaAcceptServer {

    @Autowired
    private KafkaPropertie kafkaPropertie;

    public void acceptMessage(String topic) {
        try {
            KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(getProperties());
            kafkaConsumer.subscribe(Arrays.asList(topic));
            while (true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    log.info("Kafka订阅-->{}", record.value());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", kafkaPropertie.getServers());
        properties.put("enable.auto.commit", kafkaPropertie.getEnableAutoCommit());
        properties.put("auto.commit.interval.ms", kafkaPropertie.getAutoCmmitIntervalMs());
        properties.put("auto.offset.reset", kafkaPropertie.getAutoOffsetReset());
        properties.put("session.timeout.ms", kafkaPropertie.getSessionTimeoutMs());
        properties.put("key.deserializer", kafkaPropertie.getKeySerializer());
        properties.put("value.deserializer", kafkaPropertie.getValueSerializer());
        properties.put("group.id", "groupA");
        return properties;
    }
}