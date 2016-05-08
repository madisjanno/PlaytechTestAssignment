package com.playtech.summerinternship.metric_aggregator;

import com.playtech.summerinternship.data_structures.DataPoint;
import com.playtech.summerinternship.calculator.MetricCalculator;

import java.util.*;

/**
 * Metric tracker that subdivides collected data according to time.
 */
public class TimedMetric {

    final private Map<Long, MetricCalculator> calculatorMap = new HashMap<>();
    final private MetricCalculator calculatorPrototype;

    final private String metricName;
    final private long period;

    private boolean dirty = false;

    /**
     * Constructs a new TimedMetric
     * @param metricName The name of the metric.
     * @param period The length of time to subdivide by.
     * @param calculatorPrototype The calculator of the metric being tracked.
     */
    public TimedMetric(String metricName, long period, MetricCalculator calculatorPrototype) {
        this.metricName = metricName;
        this.period = period;
        this.calculatorPrototype = calculatorPrototype;
    }

    public String getMetricName() {
        return metricName;
    }

    /**
     * @return whether the last calculation has been invalidated
     */
    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    /**
     * Adds new data. May invalidate calculation
     * @param data data to be added
     */
    public void addData(DataPoint data) {
        initializeIfNeeded(data.getTimeStamp());

        MetricCalculator calculator = calculatorMap.get(timestampTolocalStamp(data.getTimeStamp()));
        if ( calculator.addValue(data.getValue()) ) setDirty(true);
    }

    /**
     * Initializes the calculators for new time periods
     * @param timestamp the time to initialize
     */
    private void initializeIfNeeded(long timestamp) {
        long localStamp = timestampTolocalStamp(timestamp);

        if (!calculatorMap.containsKey(localStamp)) {
            calculatorMap.put(localStamp, calculatorPrototype.makeSimilarCalculator());
        }
    }

    /**
     * @param timestamp global timestamp
     * @return local timestamp
     */
    private long timestampTolocalStamp(long timestamp) {
        return timestamp/period;
    }

    /**
     * @param localStamp local timestamp
     * @return global timestamp
     */
    private long localStampToTimestamp(long localStamp) {
        return localStamp*period;
    }

    /**
     * Composes a sorted list of all datapoints that have been calculated.
     * @return List of datapoints sorted according to timestamps
     */
    public List<DataPoint> getCollatedData() {
        List<DataPoint> collatedData = new ArrayList<>();
        for (Long localStamp : new TreeSet<>(calculatorMap.keySet())) {
            collatedData.add(new DataPoint(localStampToTimestamp(localStamp), calculatorMap.get(localStamp).getCalculatedValue()));
        }
        return collatedData;
    }
}
