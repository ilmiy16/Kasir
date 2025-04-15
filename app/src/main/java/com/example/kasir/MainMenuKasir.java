package com.example.kasir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainMenuKasir extends AppCompatActivity {
    private DatabaseReference databaseRef;

    private LinearLayout cartContainer;
    private TextView totalItemsText;
    private TextView totalPriceText;
    private ImageView cartIcon;

    public int[] jumlahPesananMakanan = new int[9];
    public int[] jumlahPesananMinuman = new int[15];

    private int[] hargaMakanan = {15000, 20000, 18000, 25000, 22000, 19000, 21000, 16000, 23000}; // contoh
    private int[] hargaMinuman = {8000, 10000, 12000, 9000, 11000, 9500, 10500, 8500, 10000, 12000, 7000, 7500, 13000, 14000, 10000}; // contoh

    public int[] getJumlahPesananMakanan() {
        return jumlahPesananMakanan;
    }

    public int[] getJumlahPesananMinuman() {
        return jumlahPesananMinuman;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_kasir);

        // Inisialisasi Firebase database reference
        databaseRef = FirebaseDatabase.getInstance().getReference("pesanan"); // atau path lain

        // Inisialisasi UI komponen
        cartContainer = findViewById(R.id.cartContainer);
        totalItemsText = findViewById(R.id.totalItemsText);
        totalPriceText = findViewById(R.id.totalPriceText);
        cartIcon = findViewById(R.id.cartIcon);

        // Menyembunyikan container cart di awal
        cartContainer.setVisibility(View.GONE);

        // Bottom Navigation setup
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            // Memilih fragment berdasarkan item yang dipilih di Bottom Navigation
            if (item.getItemId() == R.id.Navigation_Makanan) {
                selectedFragment = new MakananFragment();
            } else if (item.getItemId() == R.id.navigation_Minuman) {
                selectedFragment = new MinumanFragment();
            }

            // Mengganti fragment yang tampil di layar
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main_manu_kasir, selectedFragment)
                        .commit();
            }
            return true;
        });

        // Memastikan MakananFragment tampil pertama kali saat aplikasi pertama kali dijalankan
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main_manu_kasir, new MakananFragment())
                    .commit();
        }

        cartContainer.setOnClickListener(v -> {
            // Intent untuk berpindah ke activity Pembayarankasir
            Intent intent = new Intent(MainMenuKasir.this, PembayaranKasir.class);

            // Mengirimkan data total item dan total harga
            intent.putExtra("TOTAL_ITEMS", totalItemsText.getText().toString());
            intent.putExtra("TOTAL_PRICE", totalPriceText.getText().toString());

            // Mengirimkan array jumlah pesanan makanan dan minuman
            intent.putExtra("JUMLAH_PESANAN_MAKANAN", jumlahPesananMakanan);
            intent.putExtra("JUMLAH_PESANAN_MINUMAN", jumlahPesananMinuman);

            // Mengirimkan array harga makanan dan minuman
            intent.putExtra("HARGA_MAKANAN", hargaMakanan);
            intent.putExtra("HARGA_MINUMAN", hargaMinuman);

            startActivity(intent); // Mulai activity Pembayarankasir
        });
    }

    // Method ini dipanggil oleh fragment ketika ada perubahan pesanan
    public void recalculateCart() {
        int totalItems = 0;
        int totalHarga = 0;

        // Menghitung total item dan total harga untuk makanan
        for (int i = 0; i < jumlahPesananMakanan.length; i++) {
            totalItems += jumlahPesananMakanan[i];
            totalHarga += jumlahPesananMakanan[i] * hargaMakanan[i];
        }

        // Menghitung total item dan total harga untuk minuman
        for (int i = 0; i < jumlahPesananMinuman.length; i++) {
            totalItems += jumlahPesananMinuman[i];
            totalHarga += jumlahPesananMinuman[i] * hargaMinuman[i];
        }

        // Memperbarui cart setelah perhitungan ulang
        updateCart(totalItems, totalHarga);
    }

    // Method untuk memperbarui tampilan cart
    public void updateCart(int items, int price) {
        if (items > 0) {
            cartContainer.setVisibility(View.VISIBLE);
            totalItemsText.setText(items + (items == 1 ? " item" : " items"));
            totalPriceText.setText("Rp " + price);
        } else {
            cartContainer.setVisibility(View.GONE);
        }
    }
}