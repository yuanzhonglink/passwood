package com.pass.kafka.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 *
 * 功能描述:
 *
 * @auther: yuanzhonglin
 * @date: 2018/12/14 13:17
 */
@Slf4j
@Component
public class KafkaSendServer {

    @Autowired
    private KafkaPropertie kafkaPropertie;

    /**
     * @方法名: sendMsg<br>
     * @描述: 发送消息<br>
     * @创建者: yuanzhonglin<br>
     * @修改时间: 2018年7月13日 下午5:05:52<br>
     * @param topic
     * @param msg
     */
    public void sendMsg (String topic, String msg) {
        log.info("KafkaSendServer-->sendMsg-->topic={},msg={}",topic,msg);
        try (Producer<String, String> producer = new KafkaProducer(getProperties()))
        {
            producer.send(new ProducerRecord(topic, msg),new KafkaSendCallBack());
        } catch (Exception e) {
            log.error("KafkaSendServer-->sendMsgError={}",e.getMessage());
        }
    }

    /**
     * @方法名: sendMsg<br>
     * @描述: 发送消息 K,V格式<br>
     * @创建者: yuanzhonglin<br>
     * @修改时间: 2018年7月13日 下午5:05:52<br>
     * @param topic
     * @param key
     * @param value
     */
    public void sendMsg(String topic, String key, String value) {
        log.info("KafkaSendServer-->sendMsg-->topic={},key={},value={}",topic,key,value);
        try (Producer<String, String> producer = new KafkaProducer(getProperties()))
        {
            producer.send(new ProducerRecord(topic, key, value),new KafkaSendCallBack());
        } catch (Exception e) {
            log.error("KafkaSendServer-->sendMsgError={}",e.getMessage());
        }
    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaPropertie.getServers());
        props.put("acks", kafkaPropertie.getAcks());
        props.put("retries", kafkaPropertie.getRetries());
        props.put("batch.size", kafkaPropertie.getBatchSize());
        props.put("linger.ms", kafkaPropertie.getLingerMs());
        props.put("buffer.memory", kafkaPropertie.getBufferMemory());
        props.put("compresstion.type", kafkaPropertie.getCompresstionType());
        props.put("partitioner.class", kafkaPropertie.getPartitionerClass());
        props.put("key.serializer", kafkaPropertie.getKeySerializer());
        props.put("value.serializer", kafkaPropertie.getValueSerializer());
        return props;
    }
}