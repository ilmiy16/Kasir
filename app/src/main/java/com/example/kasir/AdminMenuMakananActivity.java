package com.example.kasir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AdminMenuMakananActivity extends AppCompatActivity {

    private Button tabMakanan, tabMinuman, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_makanan_admin); // Pastikan layout benar

    }
}
