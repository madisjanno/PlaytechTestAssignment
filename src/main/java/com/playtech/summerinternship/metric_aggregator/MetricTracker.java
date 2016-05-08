package com.playtech.summerinternship.metric_aggregator;

import com.playtech.summerinternship.data_structures.DataPoint;
import com.playtech.summerinternship.data_structures.PathDataListPair;
import com.playtech.summerinternship.calculator.AverageCalculator;
import com.playtech.summerinternship.calculator.MaxCalculator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for tracking a list of metrics
 */
public class MetricTracker {
    private Map<String, List<TimedMetric>> metricsOf = new HashMap<>(); // String = path

    /**
     * Adds data to all relevant metrics
     * @param path path of the metric
     * @param data added data
     */
    public synchronized void addData(String path, DataPoint data) {
        path = path.replace(".", File.separator); // converts inputted path into actual filepath
        initializeIfNeeded(path);

        for (TimedMetric metric : metricsOf.get(path)) {
            metric.addData(data);
        }
    }

    /**
     * Initializes the tracked metrics of a new path
     * @param path the initialized path
     */
    private void initializeIfNeeded(String path) {
        if (!metricsOf.containsKey(path)) {
            metricsOf.put(path, composeTrackedMetricsList());
        }
    }

    /**
     * @return List of tracked metrics for use in initialization
     */
    private List<TimedMetric> composeTrackedMetricsList() {
        List<TimedMetric> metrics = new ArrayList<>();

        metrics.add(new TimedMetric("1SecondAvg", 1000, new AverageCalculator()));
        metrics.add(new TimedMetric("1SecondMax", 1000, new MaxCalculator()));
        metrics.add(new TimedMetric("1MinuteAvg", 60000, new AverageCalculator()));
        metrics.add(new TimedMetric("1MinuteMax", 60000, new MaxCalculator()));

        return metrics;
    }

    /**
     * Composes a list of all changed metrics and their data.
     * @return list of path and datapoint list pairs
     */
    public synchronized List<PathDataListPair> getChangedMetrics() {
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