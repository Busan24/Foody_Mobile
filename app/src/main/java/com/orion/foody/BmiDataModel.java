package com.orion.foody;

import com.google.gson.annotations.SerializedName;

public class BmiDataModel {
    @SerializedName("id")
    private String id;

    @SerializedName("nilai_bmi")
    private double nilai_bmi;

    @SerializedName("waktu")
    private String waktu;

    @SerializedName("kategori")
    private BmiCategoryModel kategori;

    // Getter methods

    public String getId() {
        return id;
    }

    public double getNilai_bmi() {
        return nilai_bmi;
    }

    public String getWaktu() {
        return waktu;
    }

    public BmiCategoryModel getKategori() {
        return kategori;
    }
}