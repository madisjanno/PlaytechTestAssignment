package com.playtech.summerinternship.data_structures;

import com.playtech.summerinternship.data_structures.DataPoint;

import java.util.List;

/**
 * Data scructure for holding a path and a list of datapoints.
 */
public class PathDataListPair {
    private final String path;
    private final List<DataPoint> data;
    private final boolean append;

    public PathDataListPair(String path, List<DataPoint> data) {
        this.path = path;
        this.data = data;
        this.append = false;
    }

    public PathDataListPair(String path, List<DataPoint> data, boolean append) {
        this.path = path;
        this.data = data;
        this.append = append;
    }

    public String getPath() {
        return path;
    }

    public List<DataPoint> getData() {
        return data;
    }

    public boolean shouldAppend() {
        return append;
    }
}
