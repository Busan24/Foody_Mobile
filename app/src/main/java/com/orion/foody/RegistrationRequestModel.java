package com.orion.foody;


// RegistrationRequestModel.java
public class RegistrationRequestModel {
    private String name;
    private String email;
//    private String username;
    private String password;
    private String jenis_kelamin;
    private String tanggal_lahir;
    private double aktivitas;
    private double tinggi_badan;
    private double berat_badan;

    public RegistrationRequestModel(String name, String email,
//                                    String username,
                                    String password, String jenis_kelamin, String tanggal_lahir, double aktivitas, double tinggi_badan, double berat_badan) {
        this.name = name;
        this.email = email;
//        this.username = username;
        this.password = password;
        this.jenis_kelamin = jenis_kelamin;
        this.tanggal_lahir = tanggal_lahir;
        this.aktivitas = aktivitas;
        this.tinggi_badan = tinggi_badan;
        this.berat_badan = berat_badan;
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

//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public double getAktivitas() {
        return aktivitas;
    }

    public void setAktivitas(double aktivitas) {
        this.aktivitas = aktivitas;
    }

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
}




