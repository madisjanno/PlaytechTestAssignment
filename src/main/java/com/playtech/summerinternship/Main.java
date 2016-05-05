package com.playtech.summerinternship;

import java.util.List;
import java.util.Random;

/**
 * Created by madis_000 on 05/05/2016.
 */
public class Main {
    public static void main(String[] args) {
        Random r = new Random();

        MetricTracker tracker = new MetricTracker();

        for (int i = 0; i < 10000; i++) {
            tracker.addData("some.path", new DataPoint(r.nextInt(1000000), r.nextInt(10000)));
        }

        List<PathDataListPair> a = tracker.getChangedMetrics();

        for (PathDataListPair pathDataListPair : a) {
            System.out.println(pathDataListPair.getPath());
            for (DataPoint dataPoint : pathDataListPair.getData()) {
                System.out.println(dataPoint);
            }
        }
    }
}
