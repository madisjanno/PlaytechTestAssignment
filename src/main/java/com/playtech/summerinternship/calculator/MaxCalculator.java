package com.playtech.summerinternship.calculator;

/**
 * Created by madis_000 on 05/05/2016.
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
    public MetricCalculator makeCopy() {
        return new MaxCalculator();
    }
}
