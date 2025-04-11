package com.example.kasir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class AdminTransaksiActivity extends AppCompatActivity {

    private Button tabKasir, tabEditMenu;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_admin); // Pastikan layout betul

        // Hubungkan tombol dengan ID-nya
        tabKasir = findViewById(R.id.tabKasir);
        tabEditMenu = findViewById(R.id.tabEditMenu);
        btnBack = findViewById(R.id.imageButton5); // tombol keluar/back

        // Aksi tombol Kasir
        tabKasir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminTransaksiActivity.this, AdminDaftarKasirActivity.class);
                startActivity(intent);
                finish(); // Optional: tutup halaman sekarang
            }
        });

        // Aksi tombol Edit Menu
        tabEditMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminTransaksiActivity.this, AdminMenuMakananActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Aksi tombol keluar/back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // kembali ke halaman sebelumnya
            }
        });
    }
}
