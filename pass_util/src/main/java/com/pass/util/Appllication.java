package com.pass.util;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author yuanzhonglin
 * @date 2019/5/20
 * @Description:
 */
@SpringBootApplication
// 启用JPA审计
@EnableJpaAuditing
// 启用缓存
@EnableCaching
// 启用异步
@EnableAsync
// 启动任务调度
@EnableScheduling
public class Appllication {

    public static void main(String[] args) {
        SpringApplication.run(Appllication.class, args);
    }
}
