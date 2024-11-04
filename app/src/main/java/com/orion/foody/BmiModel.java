package com.orion.foody;

public class BmiModel {
    private double tinggi_badan;
    private double berat_badan;
    private String id;
    private String waktu;
    private String kategori;

    private double nilai_bmi;
    private String warna_tebal;
    private String warna;

    public BmiModel(double tinggi_badan, double berat_badan) {
        this.tinggi_badan = tinggi_badan;
        this.berat_badan = berat_badan;
    }

    // Getter dan setter sesuai kebutuhan

    public double getTinggi_badan() {
        return tinggi_badan;
    }

    public void setTinggi_badan(double tinggi_badan) {
        this.tinggi_badan = tinggi_badan;
    }

    public double getBerat_badan() {
        return berat_badan;
    }

    public void setBerat_badan(double berat_badan) {
        this.berat_badan = berat_badan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public double getNilai_bmi() {
        return nilai_bmi;
    }

    public void setNilai_bmi(double nilai_bmi) {
        this.nilai_bmi = nilai_bmi;
    }

    public String getWarna_tebal() {
        return warna_tebal;
    }

    public void setWarna_tebal(String warna_tebal) {
        this.warna_tebal = warna_tebal;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }
}

