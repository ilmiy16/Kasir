package com.example.kasir;

import com.google.firebase.database.PropertyName;

public class Makanan {
    @PropertyName("Nama")
    public String nama;

    @PropertyName("Gambar")
    public String gambar;

    @PropertyName("Harga")
    public int harga;

    public Makanan() {}

    public String getNama() { return nama; }
    public String getGambar() { return gambar; }
    public int getHarga() { return harga; }

    public void setNama(String nama) { this.nama = nama; }
    public void setGambar(String gambar) { this.gambar = gambar; }
    public void setHarga(int harga) { this.harga = harga; }
}