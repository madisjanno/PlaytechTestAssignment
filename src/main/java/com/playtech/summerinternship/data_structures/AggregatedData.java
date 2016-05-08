package com.playtech.summerinternship.data_structures;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Madis on 6.05.2016.
 */

@XmlRootElement
public class AggregatedData {
    @XmlElement public String name;
    @XmlElement public List<long[]> datapoints = new ArrayList<>();

    public AggregatedData(String name, List<DataPoint> dataPointsList) {
        this.name = name;

        for (DataPoint datapoint : dataPointsList) {
            datapoints.add(datapoint.toArray());
        }
    }

    @Override
    public String toString() {
        return "AggregatedData{" +
                "datapoints=" + datapoints +
                ", name='" + name + '\'' +
                '}';
    }
}