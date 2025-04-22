package com.example.kasir;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("kasir");

        // Berpindah otomatis ke PilihanActivity setelah 2 detik (opsional)
        new android.os.Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, Pilihan.class);
            startActivity(intent);
            finish(); // Menutup MainActivity agar tidak bisa kembali dengan tombol back
        }, 5000); // 2000ms = 2 detik
    }
}
