package com.loadCsv.service;


import java.util.Map;

public class CSVStatislic  {
    private Map<String, Integer> headers;
    private Map<String, Integer> headersStatistic;
    private int recordsCount;

    public CSVStatislic(Map<String, Integer> headers, Map<String, Integer> headersStatistic, int recordsCount) {
        this.headers = headers;
        this.headersStatistic = headersStatistic;
        this.recordsCount = recordsCount;
    }

    public Map<String, Integer> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Integer> headers) {
        this.headers = headers;
    }

    public Map<String, Integer> getHeadersStatistic() {
        return headersStatistic;
    }

    public void setHeadersStatistic(Map<String, Integer> headersStatistic) {
        this.headersStatistic = headersStatistic;
    }

    public int getRecordsCount() {
        return recordsCount;
    }

    public void setRecordsCount(int recordsCount) {
        this.recordsCount = recordsCount;
    }

    public String getStaistic() {
        String statistic = "";
        statistic += "Headers: " + headers.keySet() + " ";
        statistic += "Count of Records: " + recordsCount+ " ";
        statistic += "Head with emtpty values (Name:Counts): " + headersStatistic + " ";
        return  statistic;
    }
}
