package com.playtech.summerinternship;

import com.playtech.summerinternship.data_structures.AggregatedData;
import com.playtech.summerinternship.file.FileUtility;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws Exception {
/*
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
            FileUtility.writeDataToFile(pathDataListPair);
        }

        System.out.println("------------------------");

        tracker.addData("some.path", new DataPoint(r.nextInt(1000000), 4));

        a = tracker.getChangedMetrics();
        for (PathDataListPair pathDataListPair : a) {
            System.out.println(pathDataListPair.getPath());
            for (DataPoint dataPoint : pathDataListPair.getData()) {
                System.out.println(dataPoint);
            }
            FileUtility.writeDataToFile(pathDataListPair);
        }
*/
        /*
        for (File file : FileUtility.getFilesMatchingPattern("some.*.*Avg")) {
            System.out.println(file);
            FileUtility.readDataFromFile(file, 0, 10000000);
            for (DataPoint dataPoint : FileUtility.readDataFromFile(file, 0, 10000000)) {
                System.out.println(dataPoint);
            }
        }
        */
        /*
        List<AggregatedData> data = FileUtility.getRequestedData("some.*", 0, 4000);
        System.out.println(data);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(data));
        */

        try (MetricServer server = new MetricServer(2003, 1, TimeUnit.SECONDS)) {
            server.run();
        }
    }
}
