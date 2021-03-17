package com.pass.server;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;

/**
 * @author yuanzhonglin
 * @date 2021/3/17
 */
public class CounterCollector {

    static final Counter counter = Counter.build()
            .name("inprogress_requests")
            .labelNames("method")
            .help("Inprogress requests.").register();

    public static void processRequest() {
        counter.labels("get").inc();
    }

}
