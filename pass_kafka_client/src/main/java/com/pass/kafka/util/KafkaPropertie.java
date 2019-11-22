package com.pass.kafka.util;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

// 获取resources下的文件(栗子：classpath:config/kafka.properties)
@PropertySource("classpath:kafka.properties")
@Component
@Data // get、set方法必须有
// 一种同时使用下面注解；另一种直接使用@Value注解（不需要get、set方法）
//@ConfigurationProperties(prefix = "kafka")
public class KafkaPropertie {

    @Value("${kafka.servers}")
    private String servers;

    @Value("${kafka.topic}")
    private String topic;

    @Value("${kafka.key.serializer}")
    private String keySerializer;

    @Value("${kafka.value.serializer}")
    private String valueSerializer;

    @Value("${kafka.acks}")
    private String acks;

    @Value("${kafka.retries}")
    private int retries;

    @Value("${kafka.batch.size}")
    private int batchSize;

    @Value("${kafka.linger.ms}")
    private int lingerMs;

    @Value("${kafka.buffer.memory}")
    private int bufferMemory;

    @Value("${kafka.compresstion.type}")
    private String compresstionType;

    @Value("${kafka.enable.auto.commit}")
    private String enableAutoCommit;

    @Value("${kafka.auto.commit.interval.ms}")
    private int autoCmmitIntervalMs;

    @Value("${kafka.session.timeout.ms}")
    private int sessionTimeoutMs;

    @Value("${kafka.max.poll.records}")
    private int maxPollRecords;

    @Value("${kafka.auto.offset.reset}")
    private String autoOffsetReset;
}
