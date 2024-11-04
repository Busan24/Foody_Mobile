package com.orion.foody;

// HistoryBmiModel.java

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class HistoryBmiModel {
    @SerializedName("hari")
    private String hari;

    @SerializedName("tanggal")
    private String tanggal;

    @SerializedName("data")
    private List<BmiDataModel> data;

    // Getter methods

    public String getHari() {
        return hari;
    }

    public String getTanggal() {
        return tanggal;
    }

    public List<BmiDataModel> getData() {
        return data;
    }
}

