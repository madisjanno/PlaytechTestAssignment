package com.playtech.summerinternship;

import java.util.List;

/**
 * Created by madis_000 on 05/05/2016.
 */
public class PathDataListPair {
    private final String path;
    private final List<DataPoint> data;

    public PathDataListPair(String path, List<DataPoint> data) {
        this.path = path;
        this.data = data;
    }

    public String getPath() {
        return path;
    }

    public List<DataPoint> getData() {
        return data;
    }
}
