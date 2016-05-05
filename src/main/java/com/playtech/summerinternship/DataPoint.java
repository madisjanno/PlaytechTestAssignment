package com.playtech.summerinternship;

/**
 * Created by madis_000 on 05/05/2016.
 */
public class DataPoint {
    final long timeStamp;
    final long value;

    public DataPoint(long timeStamp, long value) {
        this.timeStamp = timeStamp;
        this.value = value;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return timeStamp + " " + value;
    }
}
