package com.loadCsv.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.*;


public class LoadCSV {
    private final String PATH;
    private List<File> files = new ArrayList<File>();

    public String getPATH() {
        return PATH;
    }

    public LoadCSV(String path) {
        this.PATH = path;
    }

    private synchronized List<File> createFilesList() {
        File folder = new File(PATH);
        files.clear();
        if (!folder.exists() || !folder.isDirectory())
            throw new RuntimeException("Wrong folder init");
        File[] filesInFolder = folder.listFiles();
        for (File temp : filesInFolder) {
            String name = temp.getName();
            if (name.substring(name.lastIndexOf('.'), name.length()).equals(".csv")) {
                files.add(temp);}
        }
        return files;
    }

    public List<String> getStatistic() throws IOException{
        files = createFilesList();
        if(files.isEmpty()){
            return Arrays.asList("No .csv files in directory: " + PATH);
        }
        List<String> statistic = new ArrayList<>();
        for ( File file: files) {
            String fileStatistic = "File Name = " + file.getName() + " ";
            fileStatistic += getAllData(file).getStaistic();
            statistic.add(fileStatistic);
        }
        return statistic;
    }

    private CSVStatistic getAllData(File file) throws IOException {
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
        return new CSVStatistic(headers, headersStatistic, recordsCount);
    }
}
