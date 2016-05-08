package com.playtech.summerinternship.calculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Calculator for the average value of inputted data values.
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
    public MetricCalculator makeSimilarCalculator() {
        return new AverageCalculator();
    }
}
