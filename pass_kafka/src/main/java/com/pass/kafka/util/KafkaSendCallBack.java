package com.pass.kafka.util;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * kafka是否发送成功
 *
 * @Author yuanzhonglin
 * @since 2019/11/21
 */
@Slf4j
public class KafkaSendCallBack implements Callback{
    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        if (null != recordMetadata) {
            log.info("kafka发送成功，recordMetadata={}", JSONObject.toJSON(recordMetadata));
        } else {
            log.error("kafka发送异常,errorMessage={}",e.getMessage());
        }
    }
}
