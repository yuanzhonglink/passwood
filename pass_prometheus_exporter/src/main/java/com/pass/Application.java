package com.pass;

import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.hotspot.DefaultExports;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yuanzhonglin
 * @date 2019/5/14
 * @Description:
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        try {
            // 自定义
//            new YourCustomCollector().register();
//            new YourCustomCollector2().register();
            // 内置jvm监控
            DefaultExports.initialize();
            // Gauge类型
//            GaugeCollector.processRequest();
            // Counter类型
//            CounterCollector.processRequest();
            // PushGateway
            HTTPServer server = new HTTPServer(1234);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
