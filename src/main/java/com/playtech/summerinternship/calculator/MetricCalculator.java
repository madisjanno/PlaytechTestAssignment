package com.playtech.summerinternship.calculator;

/**
 * Created by madis_000 on 05/05/2016.
 */
public interface MetricCalculator {

    boolean addValue(long value);

    long getCalculatedValue();

    MetricCalculator makeCopy();
}
