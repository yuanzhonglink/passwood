package com.pass.prometheus.util;

import io.prometheus.client.Collector;
import io.prometheus.client.GaugeMetricFamily;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yuanzhonglin
 * @date 2020/7/28
 * @Description:
 */
class YourCustomCollector2 extends Collector {
    public List<MetricFamilySamples> collect() {
        List<MetricFamilySamples> mfs = new ArrayList<>();

        // With no labels.
        mfs.add(new GaugeMetricFamily("my_gauge_2", "help", 42));

        // With labels
        GaugeMetricFamily labeledGauge = new GaugeMetricFamily("my_other_gauge", "help", Arrays.asList("labelname"));
        labeledGauge.addMetric(Arrays.asList("foo"), 4);
        labeledGauge.addMetric(Arrays.asList("bar"), 5);
        mfs.add(labeledGauge);

        return mfs;
    }
}
