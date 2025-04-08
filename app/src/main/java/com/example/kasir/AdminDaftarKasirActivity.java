package com.example.kasir;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AdminDaftarKasirActivity extends AppCompatActivity {

    private Button btnAktif, btnHapus, btnAktif1, btnHapus1;
    private Button tabKasir, tabEditMenu, tabTransaksi;
    private ImageView btnBack;
    private TextView txtUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kasir_admin);

        // Inisialisasi View
        btnAktif = findViewById(R.id.btnAktif);
        btnHapus = findViewById(R.id.btnHapus);
        btnAktif1 = findViewById(R.id.btnAktif1);
        btnHapus1 = findViewById(R.id.btnHapus1);
        tabKasir = findViewById(R.id.tabKasir);
        tabEditMenu = findViewById(R.id.tabEditMenu);
        tabTransaksi = findViewById(R.id.tabTransaksi);
        btnBack = findViewById(R.id.btnBack);
        txtUser = findViewById(R.id.txtUser);

        // Tombol "Aktif" - Kasir 1
        btnAktif.setOnClickListener(v -> {
            btnAktif.setEnabled(false);
            btnAktif.setText("Aktif ✔");
        });

        // Tombol "Hapus" - Kasir 1
        btnHapus.setOnClickListener(v -> {
            btnAktif.setVisibility(View.GONE);
            btnHapus.setVisibility(View.GONE);
        });

        // Tombol "Aktif" - Kasir 2
        btnAktif1.setOnClickListener(v -> {
            btnAktif1.setEnabled(false);
            btnAktif1.setText("Aktif ✔");
        });

        // Tombol "Hapus" - Kasir 2
        btnHapus1.setOnClickListener(v -> {
            btnAktif1.setVisibility(View.GONE);
            btnHapus1.setVisibility(View.GONE);
        });

        // Navigasi antar tab
        tabKasir.setOnClickListener(v -> {
            tabKasir.setBackgroundResource(R.drawable.tab_selected);
            tabEditMenu.setBackgroundResource(R.drawable.tab_unselected);
            tabTransaksi.setBackgroundResource(R.drawable.tab_unselected);
        });

        // **Navigasi ke Halaman Edit Menu**
        tabEditMenu.setOnClickListener(v -> {
            tabKasir.setBackgroundResource(R.drawable.tab_unselected);
            tabEditMenu.setBackgroundResource(R.drawable.tab_selected);
            tabTransaksi.setBackgroundResource(R.drawable.tab_unselected);

            // **Langsung pindah ke AdminMenuMakananActivity**
            Intent intent = new Intent(AdminDaftarKasirActivity.this, AdminMenuMakananActivity.class);
            startActivity(intent);
        });

        // **Navigasi ke Halaman Transaksi**
        tabTransaksi.setOnClickListener(v -> {
            tabKasir.setBackgroundResource(R.drawable.tab_unselected);
            tabEditMenu.setBackgroundResource(R.drawable.tab_unselected);
            tabTransaksi.setBackgroundResource(R.drawable.tab_selected);

            Intent intent = new Intent(AdminDaftarKasirActivity.this, AdminTransaksiActivity.class);
            startActivity(intent);
        });

        // Tombol Kembali ke Halaman Sebelumnya
        btnBack.setOnClickListener(v -> finish());
    }
}
