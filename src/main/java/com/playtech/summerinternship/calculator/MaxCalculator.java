package com.playtech.summerinternship.calculator;

/**
 * Calculator for the maximum value of inputted data values.
 */
public class MaxCalculator implements MetricCalculator {
    private long max = 0;
    private boolean dirty = false;

    @Override
    public boolean addValue(long value) {
        if (value > max) {
            max = value;
            return true;
        }
        return false;
    }

    @Override
    public long getCalculatedValue() {
        return max;
    }

    @Override
    public MetricCalculator makeSimilarCalculator() {
        return new MaxCalculator();
    }
}
