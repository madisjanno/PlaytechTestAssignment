package com.playtech.summerinternship.metric_aggregator;

import com.playtech.summerinternship.DataPoint;
import com.playtech.summerinternship.calculator.MetricCalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by madis_000 on 05/05/2016.
 */
public class TimedMetric {

    final private Map<Long, MetricCalculator> calculatorMap = new HashMap<>();
    final private MetricCalculator calculatorPrototype;

    final private String metricName;
    final private long period;

    private boolean dirty = false;

    public TimedMetric(String metricName, long period, MetricCalculator calculatorPrototype) {
        this.metricName = metricName;
        this.period = period;
        this.calculatorPrototype = calculatorPrototype;
    }

    public String getMetricName() {
        return metricName;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public void addData(DataPoint data) {
        initializeIfNeeded(data.getTimeStamp());

        MetricCalculator calculator = calculatorMap.get(timestampTolocalStamp(data.getTimeStamp()));
        if (calculator.addValue(data.getValue())) setDirty(true);
    }

    private void initializeIfNeeded(long timestamp) {
        long localStamp = timestampTolocalStamp(timestamp);

        if (!calculatorMap.containsKey(localStamp)) {
            calculatorMap.put(localStamp, calculatorPrototype.makeCopy());
        }
    }

    private long timestampTolocalStamp(long timestamp) {
        return timestamp/period;
    }

    private long localStampToTimestamp(long localStamp) {
        return localStamp*period;
    }

    public List<DataPoint> getCollatedData() {
        List<DataPoint> collatedData = new ArrayList<>();
        for (Long localStamp : calculatorMap.keySet()) {
            collatedData.add(new DataPoint(localStampToTimestamp(localStamp), calculatorMap.get(localStamp).getCalculatedValue()));
        }
        return collatedData;
    }
}
