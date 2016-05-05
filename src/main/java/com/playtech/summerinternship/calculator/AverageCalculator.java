package com.playtech.summerinternship.calculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by madis_000 on 05/05/2016.
 */
public class AverageCalculator implements MetricCalculator {
    private final List<Long> values = new ArrayList<>();

    private long calculatedAverage = 0;
    private boolean dirty = false;

    @Override
    public boolean addValue(long value) {
        values.add(value);
        if (value != calculatedAverage) {
            dirty = true;
            return true;
        }
        return false;
    }

    @Override
    public long getCalculatedValue() {
        if (dirty) calculateAverage();

        return calculatedAverage;
    }

    private void calculateAverage() {
        long sum = 0;
        for (Long value : values) {
            sum += value;
        }
        calculatedAverage = sum/values.size();
        dirty = false;
    }

    @Override
    public MetricCalculator makeCopy() {
        return new AverageCalculator();
    }
}
