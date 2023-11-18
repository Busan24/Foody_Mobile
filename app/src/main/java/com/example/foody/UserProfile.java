package com.example.foody;

import com.google.gson.annotations.SerializedName;

public class UserProfile {
    private String status;
    private UserData data;
    private SummaryData summary;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

    public SummaryData getSummary() {
        return summary;
    }

    public void setSummary(SummaryData summary) {
        this.summary = summary;
    }
}


