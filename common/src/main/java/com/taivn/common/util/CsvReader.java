package com.taivn.common.util;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * The class CsvReader
 * 
 * @author hoalh
 * 
 */
public class CsvReader {

    /**
     * readAll Read csv files
     * 
     * @param reader
     * @return
     */
    public static List<String[]> readAll(Reader reader) {

        CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();

        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(0).withCSVParser(parser).build();

        List<String[]> list = new ArrayList<>();
        try {
            list = csvReader.readAll();
            reader.close();
            csvReader.close();
        } catch (Exception ex) {
            FileUtil.err(ex);
        }
        return list;
    }

    /**
     * readOneByOne Read one csv
     * 
     * @param reader
     * @return
     */
    public static List<String[]> readOneByOne(Reader reader) {
        List<String[]> list = new ArrayList<>();
        try {
            CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();

            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(0).withCSVParser(parser).build();

            String[] line;
            String[] listEmployIds = new String[0];
            int length = 0;
            boolean isFirstLine = true;

            while ((line = csvReader.readNext()) != null) {
                if (isFirstLine) {
                    listEmployIds = line;
                    length = listEmployIds.length;
                    isFirstLine = false;
                    continue;
                }
                for (int i = 1; i < length; i++) {
                    if (line[i] == "" || line[i].isEmpty())
                        line[i] = "0";

                    // timed_disease employee_id state_id
                    list.add(new String[] { line[0], listEmployIds[i], line[i] });
                }
            }
            reader.close();
            csvReader.close();
        } catch (Exception ex) {
            FileUtil.err(ex);
        }
        return list;
    }

}
