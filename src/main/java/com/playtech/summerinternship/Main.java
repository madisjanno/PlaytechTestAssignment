package com.playtech.summerinternship;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws Exception {
        try (MetricServer server = new MetricServer(2003, 1, TimeUnit.SECONDS)) {
            server.run();
        }
    }
}
