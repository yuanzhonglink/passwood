package com.pass.dubbo.client;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.pass.api.DubboApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yuanzhonglin
 * @date 2019/11/25
 */
@Component
@Slf4j
public class DubboClient implements ApplicationRunner {

    @Reference
    DubboApi dubboApi;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        int i = 1;
        while (true) {
            Map<String, Object> msg = dubboApi.getMsg(i++);
            log.info("返回={}", JSON.toJSON(msg));
            Thread.sleep(1000 * 10);
        }
    }
}
