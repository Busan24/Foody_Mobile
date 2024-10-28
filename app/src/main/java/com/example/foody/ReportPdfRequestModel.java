package com.example.foody;

public class ReportPdfRequestModel {
    String from, to;

    public ReportPdfRequestModel(java.lang.String from, java.lang.String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
