package com.playtech.summerinternship.calculator;

/**
 * Interface for a class that calculates a value based on inputted data values.
 */
public interface MetricCalculator {

    boolean addValue(long value);

    long getCalculatedValue();

    MetricCalculator makeSimilarCalculator();
}
