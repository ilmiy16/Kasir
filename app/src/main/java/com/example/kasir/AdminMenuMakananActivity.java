package com.example.kasir;

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

class AdminMenuMakananaActivity extends AppCompatActivity {

    String[] beverageNames = {
            "Sosis Bakar", "Burger", "Kentang Goreng",
            "Pisang Goreng", "Tela-Tela", "Nasi Gigit",
            "Bakso Bakar", "Banana Roll"
    };

    Integer[] beverageImages = {
            R.drawable.sosis_bakar, R.drawable.burger, R.drawable.kentang_goreng,
            R.drawable.pisang_goreng, R.drawable.tela_tela, R.drawable.nasi_gigit,
            R.drawable.bakso_bakar, R.drawable.banana_roll
    };

    int[] beverageWidths = {75, 75, 75, 75, 75, 75, 75, 75};
    int[] beverageHeights = {75, 75, 75, 75, 75, 75, 75, 75};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_makanan_admin);

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
            View view = getLayoutInflater().inflate(R.layout.item_beverage, null);

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
                Toast.makeText(AdminMenuMakananaActivity.this, "Edit " + beverageNames[position], Toast.LENGTH_SHORT).show();
            });

            deleteButton.setOnClickListener(v -> {
                Toast.makeText(AdminMenuMakananaActivity.this, "Delete " + beverageNames[position], Toast.LENGTH_SHORT).show();
            });

            return view;
        }
    }
}