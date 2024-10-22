package com.example.foody;

public class UserData {
    private String name;
    private String email;
    private String jenis_kelamin;
    private int usia;
    private int berat_badan;
    private int tinggi_badan;
    private String tanggla_lahir;
    private String gambar;
    private boolean verified;
    private boolean premium;

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public int getUsia() {
        return usia;
    }

    public void setUsia(int usia) {
        this.usia = usia;
    }

    public int getBerat_badan() {
        return berat_badan;
    }

    public void setBerat_badan(int berat_badan) {
        this.berat_badan = berat_badan;
    }

    public int getTinggi_badan() {
        return tinggi_badan;
    }

    public void setTinggi_badan(int tinggi_badan) {
        this.tinggi_badan = tinggi_badan;
    }

    public String getTanggla_lahir() {
        return tanggla_lahir;
    }

    public void setTanggla_lahir(String tanggla_lahir) {
        this.tanggla_lahir = tanggla_lahir;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
