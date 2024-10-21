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

    public void setMakanan(java.lang.String makanan) {
        this.makanan = makanan;
    }

}
