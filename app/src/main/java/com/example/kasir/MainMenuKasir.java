package com.example.kasir;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenuKasir extends AppCompatActivity {

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

        cartContainer = findViewById(R.id.cartContainer);
        totalItemsText = findViewById(R.id.totalItemsText);
        totalPriceText = findViewById(R.id.totalPriceText);
        cartIcon = findViewById(R.id.cartIcon);

        cartContainer.setVisibility(View.GONE);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.Navigation_Makanan) {
                selectedFragment = new MakananFragment();
            } else if (item.getItemId() == R.id.navigation_Minuman) {
                selectedFragment = new MinumanFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main_manu_kasir, selectedFragment)
                        .commit();
            }
            return true;
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main_manu_kasir, new MakananFragment())
                    .commit();
        }

        cartContainer.setOnClickListener(v -> {
            // intent ke halaman checkout nanti
        });
    }

    // ✅ Method ini dipanggil oleh fragment ketika ada perubahan pesanan
    public void recalculateCart() {
        int totalItems = 0;
        int totalHarga = 0;

        for (int i = 0; i < jumlahPesananMakanan.length; i++) {
            totalItems += jumlahPesananMakanan[i];
            totalHarga += jumlahPesananMakanan[i] * hargaMakanan[i];
        }

        for (int i = 0; i < jumlahPesananMinuman.length; i++) {
            totalItems += jumlahPesananMinuman[i];
            totalHarga += jumlahPesananMinuman[i] * hargaMinuman[i];
        }

        updateCart(totalItems, totalHarga);
    }

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