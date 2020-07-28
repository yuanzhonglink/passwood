package com.pass.prometheus.util;

import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.hotspot.DefaultExports;

import java.io.IOException;

/**
 * @author yuanzhonglin
 * @date 2020/7/28
 * @Description:
 */
public class CustomExporter {
    public static void main(String[] args) throws IOException {
        DefaultExports.initialize();
        new YourCustomCollector().register();
        new YourCustomCollector2().register();
        HTTPServer server = new HTTPServer(1234);
    }
}
