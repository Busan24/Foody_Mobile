package com.example.foody;

public class UpdateProfileRequest {

    private String name;
    private String email;
    private String username;
    private String tanggal_lahir;
    private double aktivitas;

    private String jenis_kelamin;

    // existing methods...

    public String getJenisKelamin() {
        return jenis_kelamin;
    }

    public void setJenisKelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
