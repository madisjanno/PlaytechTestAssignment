package com.playtech.summerinternship.data_structures;

/**
 * Data structure for holding a timestamp and a value.
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

    public long[] toArray() {
        return new long[] {timeStamp, value};
    }

    @Override
    public String toString() {
        return timeStamp + " " + value;
    }
}
