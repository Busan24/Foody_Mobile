package com.example.foody;

public class CatatanMakananModel {
    private String nama;
    private String waktu;
    private int jumlah;

    private String nama_makanan;

    private String id;
    private double karbohidrat;
    private double protein;
    private double garam;
    private double gula;
    private double lemak;

    // Add these fields
    private double daily_garam;
    private double batas_garam;

    // Add getters and setters for the new fields

    public double getDaily_garam() {
        return daily_garam;
    }

    public void setDaily_garam(double daily_garam) {
        this.daily_garam = daily_garam;
    }

    public double getBatas_garam() {
        return batas_garam;
    }

    public void setBatas_garam(double batas_garam) {
        this.batas_garam = batas_garam;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public double getKarbohidrat() {
        return karbohidrat;
    }

    public void setKarbohidrat(double karbohidrat) {
        this.karbohidrat = karbohidrat;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getGaram() {
        return garam;
    }

    public void setGaram(double garam) {
        this.garam = garam;
    }

    public double getGula() {
        return gula;
    }

    public void setGula(double gula) {
        this.gula = gula;
    }

    public double getLemak() {
        return lemak;
    }

    public void setLemak(double lemak) {
        this.lemak = lemak;
    }

    public String getNama_makanan() {
        return nama_makanan;
    }

    public void setNama_makanan(String nama_makanan) {
        this.nama_makanan = nama_makanan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
