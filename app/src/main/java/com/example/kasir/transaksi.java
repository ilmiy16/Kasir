package com.example.kasir; // Sesuaikan dengan nama package aplikasi kamu

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class transaksi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi); // Pastikan nama file layout sesuai dengan yang ada di res/layout

        // Inisialisasi tombol keluar
        ImageButton btnKeluar = findViewById(R.id.imageButton5);
        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Menutup aktivitas transaksi dan kembali ke sebelumnya
            }
        });
    }
}