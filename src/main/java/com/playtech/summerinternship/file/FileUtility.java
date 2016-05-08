package com.playtech.summerinternship.file;

import com.playtech.summerinternship.data_structures.AggregatedData;
import com.playtech.summerinternship.data_structures.DataPoint;
import com.playtech.summerinternship.data_structures.PathDataListPair;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by madis_000 on 05/05/2016.
 */
public class FileUtility {
    private static final Path defaultRoot = Paths.get("metrics");

    /**
     * Writes data to the file specified by path. File is formatted in text format. Every line contains two values – timestamp and value.
     * @param data path and data to be written
     * @throws IOException
     */
    public static void writeDataToFile(PathDataListPair data) throws IOException {

        Path path = defaultRoot.resolve(data.getPath());
        Files.createDirectories(path.getParent());

        try (FileWriter writer = new FileWriter(path.toFile(), data.shouldAppend())) {
            for (DataPoint dataPoint : data.getData()) {
                writer.write(dataPoint.toString());
                writer.write(System.lineSeparator());
            }
        }
    }

    /**
     * Reads data from the file specified by path.
     * Assumes file is formatted in text format and every line contains two values – timestamp and value, and sorted according to timestamp.
     * @param file path of the file to be read
     * @param start 
     * @param end
     * @return
     * @throws IOException
     */
    public static List<DataPoint> readDataFromFile(Path file, long start, long end) throws IOException {
        List<DataPoint> data = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file.toFile()))) {

            for (String line = reader.readLine(); line != null; line = reader.readLine() ) {
                String[] part = line.split(" "); // timestamp / value
                long timestamp = Long.parseLong(part[0]);
                if (timestamp > end) break;
                if (timestamp >= start) {
                    data.add( new DataPoint( timestamp, Long.parseLong(part[1]) ) );
                }
            }

        }
        return data;
    }

    public static List<AggregatedData> getRequestedData(String pattern, long start, long end) throws IOException {
        DataComposingFileVisitor visitor = new DataComposingFileVisitor(pattern, start, end);
        Files.walkFileTree(defaultRoot, visitor);
        return visitor.getComposedData();
    }

    private static class DataComposingFileVisitor extends SimpleFileVisitor<Path> {
        private List<AggregatedData> composedData = new ArrayList<>();
        private final Pattern fileRegex;
        private long start;
        private long end;

        public DataComposingFileVisitor(String fileRegex, long start, long end) {
            this.fileRegex = Pattern.compile(fileRegex);
            this.start = start;
            this.end = end;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Path pathWithoutRoot = defaultRoot.relativize(file);
            if ( fileRegex.matcher(pathWithoutRoot.toString()).matches() ) {
                String pathName = pathWithoutRoot.toString().replace(File.separator, ".");

                composedData.add(new AggregatedData(pathName, readDataFromFile(file, start, end)));
            }
            return super.visitFile(file, attrs);
        }

        public List<AggregatedData> getComposedData() {
            return composedData;
        }
    }
}
