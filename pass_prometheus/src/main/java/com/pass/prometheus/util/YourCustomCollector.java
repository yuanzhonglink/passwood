package com.pass.prometheus.util;

import io.prometheus.client.Collector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yuanzhonglin
 * @date 2020/7/28
 * @Description:
 */
public class YourCustomCollector extends Collector {
    public List<MetricFamilySamples> collect() {
        List<MetricFamilySamples> mfs = new ArrayList<>();

        String metricName = "my_guage_1";

        // Your code to get metrics

        MetricFamilySamples.Sample sample = new MetricFamilySamples.Sample(metricName, Arrays.asList("l1"), Arrays.asList("v1"), 4);
        MetricFamilySamples.Sample sample2 = new MetricFamilySamples.Sample(metricName, Arrays.asList("l1", "l2"), Arrays.asList("v1", "v2"), 3);

        MetricFamilySamples samples = new MetricFamilySamples(metricName, Type.GAUGE, "help", Arrays.asList(sample, sample2));

        mfs.add(samples);
        return mfs;
    }
}
