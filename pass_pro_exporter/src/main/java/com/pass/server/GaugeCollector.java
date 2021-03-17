package com.pass.server;

import io.prometheus.client.Gauge;

/**
 * @author yuanzhonglin
 * @date 2021/3/17
 */
public class GaugeCollector {
    static final Gauge inprogressRequests = Gauge.build()
            .name("inprogress_requests")
            .labelNames("method")
            .help("Inprogress requests.").register();

    public static void processRequest() {
        inprogressRequests.labels("get").inc();
        // Your code here.
        inprogressRequests.labels("get").dec();
    }

}
