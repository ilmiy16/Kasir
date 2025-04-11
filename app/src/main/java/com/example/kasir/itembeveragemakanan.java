package com.example.kasir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kasir.R;

class itembeveragemakanan extends AppCompatActivity {

    String[] beverageNames = {
            "Sosis Bakar", "Burger", "Kentang Goreng",
            "Pisang Goreng", "Tela-tela", "Nasi Gigit",
            "Bakso Bakar", "Banana Roll"
    };

    Integer[] beverageImages = {
            R.drawable.sosis_bakar, R.drawable.burger, R.drawable.kentang_goreng,
            R.drawable.pisang_goreng, R.drawable.tela_tela, R.drawable.nasi_gigit,
            R.drawable.bakso_bakar, R.drawable.banana_roll
    };

    int[] beverageWidths = {75, 53, 70, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75};
    int[] beverageHeights = {75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75, 75};

    int[] beveragePrices = {10000, 15000, 10000, 10000, 15000, 10000, 10000, 15000};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_beverage_makanan); // Layout utama yang berisi RecyclerView

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 kolom grid
        recyclerView.setAdapter(new BeverageAdapter());
    }

    private class BeverageAdapter extends RecyclerView.Adapter<BeverageAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView beverageName, priceTextView;
            ImageView beverageImage;
            ImageButton addToCartButton;

            ViewHolder(View itemView) {
                super(itemView);
                beverageName = itemView.findViewById(R.id.beverageName);
                beverageImage = itemView.findViewById(R.id.beverageImage);
                priceTextView = itemView.findViewById(R.id.priceTextViewMenu);
                addToCartButton = itemView.findViewById(R.id.addToCartButton);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_beverage_makanan, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.beverageName.setText(beverageNames[position]);
            holder.beverageImage.setImageResource(beverageImages[position]);
            holder.priceTextView.setText("Rp " + beveragePrices[position]);

            holder.addToCartButton.setOnClickListener(v ->
                    Toast.makeText(itembeveragemakanan.this, "Ditambahkan: " + beverageNames[position], Toast.LENGTH_SHORT).show()
            );
        }

        @Override
        public int getItemCount() {
            return beverageNames.length;
        }
    }
}