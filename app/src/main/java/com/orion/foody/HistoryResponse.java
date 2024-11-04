package com.orion.foody;

import java.util.List;

public class HistoryResponse {
    private String hari;
    private String tanggal;
    private List<CatatankuData> data;

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public List<CatatankuData> getData() {
        return data;
    }

    public void setData(List<CatatankuData> data) {
        this.data = data;
    }
}
