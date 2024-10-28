package com.example.foody;

import java.text.NumberFormat;
import java.util.Locale;

public class TransactionData {
    private String id;
    private String nama_paket;
    private String metode_pembayaran;
    private String no_transaksi;
    private int jumlah_bayar;
    private String status;
    private String tanggal;
    private String jam;

    public TransactionData(java.lang.String id, java.lang.String nama_paket, java.lang.String metode_pembayaran, java.lang.String no_transaksi, int jumlah_bayar, java.lang.String status, java.lang.String tanggal, java.lang.String jam) {
        this.id = id;
        this.nama_paket = nama_paket;
        this.metode_pembayaran = metode_pembayaran;
        this.no_transaksi = no_transaksi;
        this.jumlah_bayar = jumlah_bayar;
        this.status = status;
        this.tanggal = tanggal;
        this.jam = jam;
    }

    public String getId() {
        return id;
    }

    public String getNama_paket() {
        return nama_paket;
    }

    public String getMetode_pembayaran() {
        return metode_pembayaran;
    }

    public String getNo_transaksi() {
        return no_transaksi;
    }

    public int getJumlah_bayar() {
        return jumlah_bayar;
    }

    public String getStatus() {
        return status;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getJam() {
        return jam;
    }

    public String getJumlah_PembayaranRupiah () {
        Locale indonesia = new Locale("id", "ID");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(indonesia);

        String formattedAmount = currencyFormat.format(jumlah_bayar);

        return  formattedAmount;
    }
}
