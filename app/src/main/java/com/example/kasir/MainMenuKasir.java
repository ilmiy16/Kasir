package com.example.kasir;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.kasir.ui.makanan.MakananFragment;
import com.example.kasir.ui.minuman.MinumanFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenuKasir extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_kasir);

        // Inisialisasi BottomNavigationView
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Set event klik untuk Bottom Navigation
        navView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.Navigation_Makanan) {
                selectedFragment = new MakananFragment();
            } else if (item.getItemId() == R.id.navigation_Minuman) {
                selectedFragment = new MinumanFragment();
            }

            // Tampilkan fragment yang dipilih
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main_manu_kasir, selectedFragment)
                        .commit();
            }
            return true;
        });

        // Set default fragment (misalnya MakananFragment saat pertama kali dibuka)
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main_manu_kasir, new MakananFragment())
                    .commit();
        }
    }
}