package com.example.kasir;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kasir.databinding.FragementMakananBinding;
import com.example.kasir.databinding.FragementMakananBinding;

public class MakananFragment extends Fragment {
    private FragementMakananBinding binding;

    String[] makananNames = {
            "Sosis Bakar", "Burger", "Kentang Goreng",
            "Pisang Goreng", "Tela-tela", "Nasi Gigit",
            "Bakso Bakar", "Banana Roll", "sosis bakar"
    };

    int[] makananImages = {
            R.drawable.sosis_bakar, R.drawable.burger, R.drawable.kentang_goreng,
            R.drawable.pisang_goreng, R.drawable.tela_tela, R.drawable.nasi_gigit,
            R.drawable.bakso_bakar, R.drawable.banana_roll, R.drawable.sosis_bakar
    };

    int[] makananPrices = {
            10000, 15000, 10000, 10000, 15000, 10000, 10000, 15000, 12314
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragementMakananBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = binding.recyclerViewMakanan;

        int columnCount = calculateNoOfColumns(160); // lebar 1 item (dp)
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), columnCount));
        recyclerView.setAdapter(new MakananAdapter());
    }


    // Hitung jumlah kolom berdasarkan lebar layar
    private int calculateNoOfColumns(float itemWidthDp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        return Math.max(2, (int) (screenWidthDp / itemWidthDp + 0.5));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class MakananAdapter extends RecyclerView.Adapter<MakananAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView namaMakanan, hargaMakanan;
            ImageView gambarMakanan;
            ImageButton tambahButton;

            ViewHolder(View itemView) {
                super(itemView);
                namaMakanan = itemView.findViewById(R.id.beverageNameMenu);
                gambarMakanan = itemView.findViewById(R.id.beverageImageMenu);
                hargaMakanan = itemView.findViewById(R.id.priceTextViewMenu);
                tambahButton = itemView.findViewById(R.id.addToCartButton);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_beverage, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.namaMakanan.setText(makananNames[position]);
            holder.gambarMakanan.setImageResource(makananImages[position]);
            holder.hargaMakanan.setText("Rp " + makananPrices[position]);

            holder.tambahButton.setOnClickListener(v -> {
                Toast.makeText(getContext(), makananNames[position] + " ditambahkan ke pesanan", Toast.LENGTH_SHORT).show();
            });
        }

        @Override
        public int getItemCount() {
            return makananNames.length;
        }
    }
}