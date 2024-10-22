package com.example.foody;

public class GenerateMakananRequestModel {
    private String makanan;
    private String detail;

    public GenerateMakananRequestModel(java.lang.String makanan, java.lang.String detail) {
        this.makanan = makanan;
        this.detail = detail;
    }

    public String getMakanan() {
        return makanan;
    }

    public void setMakanan(String makanan) {
        this.makanan = makanan;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(java.lang.String detail) {
        this.detail = detail;
    }
}
