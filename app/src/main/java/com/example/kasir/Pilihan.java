package com.example.kasir;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Pilihan extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilihan);

        // Inisialisasi tombol
        Button btnAdmin = findViewById(R.id.btnAdmin);
        Button btnKasir = findViewById(R.id.btnKasir);

        // Tombol Admin
        btnAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(Pilihan.this, LoginActivity.class);
            intent.putExtra("ROLE", "admin"); // Kirim data role admin
            startActivity(intent);
        });

        // Tombol Kasir
        btnKasir.setOnClickListener(v -> {
            Intent intent = new Intent(Pilihan.this, LoginKasirActivity.class);
            intent.putExtra("ROLE", "kasir"); // Kirim data role kasir
            startActivity(intent);
        });
    }
}
