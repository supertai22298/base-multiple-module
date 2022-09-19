package com.taivn.common.util;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.nio.file.Path;
import java.util.List;

/**
 * The class CsvWriter
 * 
 * @author hoalh
 *
 */
public class CsvWriter {

    /**
     * csvWriterOneByOne
     * 
     * @param stringArray
     * @param path
     * @return
     */
    public static String csvWriterOneByOne(List<String[]> stringArray, Path path) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(path.toString()));
            for (String[] array : stringArray) {
                writer.writeNext(array);
            }
            writer.close();
        } catch (Exception ex) {
            FileUtil.err(ex);
        }
        return FileUtil.readFile(path);
    }

    /**
     * csvWriterAll
     * 
     * @param stringArray
     * @param path
     * @return
     */
    public static String csvWriterAll(List<String[]> stringArray, Path path) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(path.toString()));
            writer.writeAll(stringArray);
            writer.close();
        } catch (Exception ex) {
            FileUtil.err(ex);
        }
        return FileUtil.readFile(path);
    }
}