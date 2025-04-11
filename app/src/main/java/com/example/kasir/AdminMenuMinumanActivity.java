package com.example.kasir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminMenuMinumanActivity extends AppCompatActivity {

    private Button tabKasir, tabEditMenu, tabTransaksi, tabMakanan;
    private ImageView btnBack;

    String[] beverageNames = {
            "Green Tea", "Thai Tea", "Es Milo",
            "Lemon Tea", "Es Taro", "Es Strawberry",
            "Es Coklat", "Es Teh", "Es Oreo", "Pop Ice","Es Leci",
            "Es Red Velvet", "Kopi Susu Panas", " Es Kopi Susu"
    };

    Integer[] beverageImages = {
            R.drawable.greentea, R.drawable.thaitea, R.drawable.esmilo,
            R.drawable.lemontea, R.drawable.estaro, R.drawable.esstrawberry,
            R.drawable.escoklat, R.drawable.esteh, R.drawable.esoreo, R.drawable.popice, R.drawable. esleci,
            R.drawable.redvelvet, R.drawable.kopsuspanas, R.drawable.kopsus,
    };

    int[] beverageWidths = {75, 75, 75, 75, 75, 75, 75, 75, 75, 75,75,75,75,75};
    int[] beverageHeights = {75, 75, 75, 75, 75, 75, 75, 75, 75, 75,75,75,75,75};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_minuman_admin);

        // Inisialisasi tombol navigasi
        tabKasir = findViewById(R.id.tabKasir);
        tabEditMenu = findViewById(R.id.tabEditMenu);
        tabTransaksi = findViewById(R.id.tabTransaksi);
        btnBack = findViewById(R.id.backButton);
        tabMakanan = findViewById(R.id.tabMakanan);


        // Tombol back
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Navigasi ke halaman Kasir
        tabKasir.setOnClickListener(v -> {
            tabKasir.setBackgroundResource(R.drawable.tab_selected);
            tabEditMenu.setBackgroundResource(R.drawable.tab_unselected);
            tabTransaksi.setBackgroundResource(R.drawable.tab_unselected);

            Intent intent = new Intent(AdminMenuMinumanActivity.this, AdminDaftarKasirActivity.class);
            startActivity(intent);
            finish();
        });

        // Navigasi ke halaman Edit Menu (saat ini halaman aktif)
        tabEditMenu.setOnClickListener(v -> {
            tabKasir.setBackgroundResource(R.drawable.tab_unselected);
            tabEditMenu.setBackgroundResource(R.drawable.tab_selected);
            tabTransaksi.setBackgroundResource(R.drawable.tab_unselected);

            // Tidak perlu berpindah activity, karena ini halaman saat ini
        });

        tabMakanan.setOnClickListener(v -> {
            tabKasir.setBackgroundResource(R.drawable.tab_unselected);
            tabEditMenu.setBackgroundResource(R.drawable.tab_unselected);
            tabTransaksi.setBackgroundResource(R.drawable.tab_unselected);
            tabMakanan.setBackgroundResource(R.drawable.tab_selected);

            Intent intent = new Intent(AdminMenuMinumanActivity.this, AdminMenuMakananActivity.class);
            startActivity(intent);
            finish();
        });


        // Navigasi ke halaman Transaksi
        tabTransaksi.setOnClickListener(v -> {
            tabKasir.setBackgroundResource(R.drawable.tab_unselected);
            tabEditMenu.setBackgroundResource(R.drawable.tab_unselected);
            tabTransaksi.setBackgroundResource(R.drawable.tab_selected);

            Intent intent = new Intent(AdminMenuMinumanActivity.this, AdminTransaksiActivity.class);
            startActivity(intent);
            finish();
        });

        // GridView isi menu
        GridView gridView = findViewById(R.id.gridView);
        gridView.setAdapter(new BeverageAdapter());
    }

    private class BeverageAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return beverageNames.length;
        }

        @Override
        public Object getItem(int position) {
            return beverageNames[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.item_beverage_minuman, null);

            TextView beverageName = view.findViewById(R.id.beverageName);
            ImageView beverageImage = view.findViewById(R.id.beverageImage);
            Button editButton = view.findViewById(R.id.editButton);
            ImageButton deleteButton = view.findViewById(R.id.deleteButton);

            beverageName.setText(beverageNames[position]);
            beverageImage.setImageResource(beverageImages[position]);

            int widthInPx = (int) (beverageWidths[position] * view.getResources().getDisplayMetrics().density);
            int heightInPx = (int) (beverageHeights[position] * view.getResources().getDisplayMetrics().density);

            ViewGroup.LayoutParams layoutParams = beverageImage.getLayoutParams();
            layoutParams.width = widthInPx;
            layoutParams.height = heightInPx;
            beverageImage.setLayoutParams(layoutParams);

            editButton.setOnClickListener(v -> {
                Intent intent = new Intent(AdminMenuMinumanActivity.this, editmenu.class);
                intent.putExtra("nama_minuman", beverageNames[position]);
                startActivity(intent);
            });

            deleteButton.setOnClickListener(v -> {
                Toast.makeText(AdminMenuMinumanActivity.this, "Delete " + beverageNames[position], Toast.LENGTH_SHORT).show();
            });

            return view;
        }
    }
}
