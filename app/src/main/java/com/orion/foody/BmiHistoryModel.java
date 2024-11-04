package com.orion.foody;

public class BmiHistoryModel {
    private String id;
    private double nilai_bmi;
    private String waktu;
    private KategoriModel kategori;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getNilai_bmi() {
        return nilai_bmi;
    }

    public void setNilai_bmi(double nilai_bmi) {
        this.nilai_bmi = nilai_bmi;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public KategoriModel getKategori() {
        return kategori;
    }

    public void setKategori(KategoriModel kategori) {
        this.kategori = kategori;
    }
}
