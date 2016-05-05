package com.playtech.summerinternship;

import com.playtech.summerinternship.calculator.AverageCalculator;
import com.playtech.summerinternship.calculator.MaxCalculator;
import com.playtech.summerinternship.metric_aggregator.TimedMetric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by madis_000 on 05/05/2016.
 */
public class MetricTracker {
    Map<String, List<TimedMetric>> metricsOf = new HashMap<>();

    public void addData(String path, DataPoint data) {
        initializeIfNeeded(path);

        for (TimedMetric metric : metricsOf.get(path)) {
            metric.addData(data);
        }
    }

    private void initializeIfNeeded(String path) {
        if (!metricsOf.containsKey(path)) {
            metricsOf.put(path, composeTrackedMetricsList());
        }
    }

    private List<TimedMetric> composeTrackedMetricsList() {
        List<TimedMetric> metrics = new ArrayList<>();

        metrics.add(new TimedMetric("1SecondAvg", 1000, new AverageCalculator()));
        metrics.add(new TimedMetric("1SecondMax", 1000, new MaxCalculator()));
        metrics.add(new TimedMetric("1MinuteAvg", 60000, new AverageCalculator()));
        metrics.add(new TimedMetric("1MinuteMax", 60000, new MaxCalculator()));

        return metrics;
    }

    public List<PathDataListPair> getChangedMetrics() {
        List<PathDataListPair> changedFiles = new ArrayList<>();
        for (String path : metricsOf.keySet()) {
            for (TimedMetric timedMetric : metricsOf.get(path)) {
                if (!timedMetric.isDirty()) continue;

                String fullPath = path + "." + timedMetric.getMetricName();
                changedFiles.add(new PathDataListPair(fullPath, timedMetric.getCollatedData()));
                timedMetric.setDirty(false);
            }
        }
        return changedFiles;
    }
}