package com.playtech.summerinternship;

import com.playtech.summerinternship.data_structures.DataPoint;
import com.playtech.summerinternship.data_structures.PathDataListPair;
import com.playtech.summerinternship.file.FileUtility;
import com.playtech.summerinternship.metric_aggregator.MetricTracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Server that automatically collects data and writes it to files at specified intervals.
 */
public class MetricServer implements AutoCloseable, Runnable {
    private final ServerSocket serverSocket;
    private final MetricTracker metricTracker = new MetricTracker();
    private final ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
    private boolean closed = false;

    /**
     * Constructs a metric tracking server and schedules periodic file updates.
     * @param port The port the server will monitor
     * @param fileUpdateWait Time period between file updates
     * @param timeUnit Time period between file updates
     * @throws IOException
     */
    public MetricServer(int port, long fileUpdateWait, TimeUnit timeUnit) throws IOException {
        serverSocket = new ServerSocket(port);
        service.scheduleAtFixedRate(new FileUpdater(), 0, fileUpdateWait, timeUnit);
    }

    /**
     * Constructs a metric tracking server.
     * @param port The port the server will monitor
     * @throws IOException
     */
    public MetricServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        try {
            while(!closed) {
                Socket connectionSocket = serverSocket.accept();
                new Thread( new ConnectionHandler(connectionSocket) ).start();
            }
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void close() throws Exception {
        closed = true;
        serverSocket.close();
        service.shutdown();
    }

    /**
     * Calculates metric values and writes updates to files.
     * @throws IOException
     */
    public void writeChangedMetricsToFiles() throws IOException {
        synchronized (filewritelock) {
            List<PathDataListPair> changedMetrics = metricTracker.getChangedMetrics();
            for (PathDataListPair changedMetric : changedMetrics) {
                FileUtility.writeDataToFile(changedMetric);
            }
        }
    }
    private final Object filewritelock = new Object();

    private class ConnectionHandler implements Runnable {
        private final Socket socket;

        public ConnectionHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try ( BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())) ){
                String inMessage = in.readLine();
                String[] parts = inMessage.split(" "); // path, value, timestamp
                metricTracker.addData( parts[0], new DataPoint( Long.parseLong(parts[2]), Long.parseLong(parts[1]) ) );
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            finally {
                try {
                    socket.close();
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private class FileUpdater implements Runnable {
        @Override
        public void run() {
            try {
                writeChangedMetricsToFiles();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
