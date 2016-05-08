package com.playtech.summerinternship.data_structures;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Madis on 6.05.2016.
 */

@XmlRootElement
public class DataRequest {
    @XmlElement private String pattern;
    @XmlElement private long start;
    @XmlElement private long end;

    public DataRequest() {
    }

    public DataRequest(String pattern, long start, long end) {
        this.end = end;
        this.pattern = pattern;
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public String getPattern() {
        return pattern;
    }

    public long getStart() {
        return start;
    }
}
