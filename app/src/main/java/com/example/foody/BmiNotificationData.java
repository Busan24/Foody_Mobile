package com.example.foody;

public class BmiNotificationData {
    private double nilaiBmi;
    private String kategori;
    private String warna;
    private String warnaTebal;

    public BmiNotificationData(double nilaiBmi, String kategori, String warna, String warnaTebal) {
        this.nilaiBmi = nilaiBmi;
        this.kategori = kategori;
        this.warna = warna;
        this.warnaTebal = warnaTebal;
    }

    public double getNilaiBmi() {
        return nilaiBmi;
    }

    public String getKategori() {
        return kategori;
    }

    public String getWarna() {
        return warna;
    }

    public String getWarnaTebal() {
        return warnaTebal;
    }
}

