package com.example.kasir;

public class ItemPesanan {
    private String nama;
    private int jumlah;
    private double harga;
    private String jam;
    private String tanggal;

    // Constructor
    public ItemPesanan(String nama, int jumlah, double harga, String jam, String tanggal) {
        this.nama = nama;
        this.jumlah = jumlah;
        this.harga = harga;
        this.jam = jam;
        this.tanggal = tanggal;
    }

    // Getters and Setters
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}