package com.playtech.summerinternship;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by madis_000 on 05/05/2016.
 */
public class FileUtility {
    static void writeData(PathDataListPair data) throws IOException {
        String[] huh = data.getPath().split(".");
        Path p = Paths.get(".\\");
        for (String s : huh) {
            p = p.resolve(s);
        }

        FileWriter writer = new FileWriter(p.toFile());
    }
}
