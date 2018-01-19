package com.loadCsv.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LoadCSV {
    private String path;
    private List<File> files = new ArrayList<File>();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public LoadCSV(String path) {
        this.path = path;
        createFilesLists();
    }

    private void createFilesLists() {
        File folder = new File(path);
        if (!folder.exists() || !folder.isDirectory())
            throw new RuntimeException("Wrong foder init");
        File[] files = folder.listFiles();
        for (File temp : files) {
            String name = temp.getName();
            if (name.substring(name.lastIndexOf('.'), name.length()).equals(".csv")) {
                this.files.add(temp);}
        }
    }

    public CSVStatislic getAllData(File file) throws IOException {
            int recordsCount;
            Map<String, Integer> headers;
            Map<String, Integer> headersStatistic = new HashMap<>();
            try (Reader reader = Files.newBufferedReader(file.toPath());
                 CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                         .withFirstRecordAsHeader()
                         .withIgnoreHeaderCase()
                         .withTrim());
            ) {
                List<CSVRecord> csvRecords = csvParser.getRecords();
                recordsCount = csvRecords.size();
                headers = csvParser.getHeaderMap();
                for (CSVRecord csvRecord : csvRecords) {
                    for (String key : headers.keySet()) {
                        if (csvRecord.get(key).isEmpty()) {
                            if (headersStatistic.get(key) == null)
                                headersStatistic.put(key, 1);
                            else
                                headersStatistic.put(key,headersStatistic.get(key)+1);
                        }
                    }
                }

            }
        return new CSVStatislic(headers, headersStatistic, recordsCount);
    }
}
