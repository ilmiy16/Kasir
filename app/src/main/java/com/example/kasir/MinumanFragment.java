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

import com.example.kasir.databinding.FregmentMinumanBinding;

public class MinumanFragment extends Fragment {

    private FregmentMinumanBinding binding;

    String[] beverageNames = {
            "Green tea", "Thai tea", "Es Milo",
            "Lemon tea", "Es Taro", "Es Strawberry",
            "Es Coklat", "Es Teh", "Es Oreo",
            "Pop Ice", "Pop Ice Small", "Es Leci",
            "Es Red Velvet", "Kopi Susu Panas", "Es Kopi Susu"
    };

    int[] beverageImages = {
            R.drawable.greentea, R.drawable.thaitea, R.drawable.esmilo,
            R.drawable.lemontea, R.drawable.estaro, R.drawable.esstrawberry,
            R.drawable.escoklat, R.drawable.esteh, R.drawable.esoreo,
            R.drawable.popice, R.drawable.popice, R.drawable.esleci,
            R.drawable.redvelvet, R.drawable.kopsuspanas, R.drawable.kopsus
    };

    int[] beveragePrices = {
            10000, 15000, 10000, 10000, 15000, 10000,
            10000, 15000, 10000, 10000, 10000, 10000,
            15000, 12000, 15000
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FregmentMinumanBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = binding.recyclerViewMinuman;

        int columnCount = calculateNoOfColumns(160); // misalnya 160dp per item
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), columnCount));
        recyclerView.setAdapter(new MinumanAdapter());
    }

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

    private class MinumanAdapter extends RecyclerView.Adapter<MinumanAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView beverageName, priceTextView;
            ImageView beverageImage;
            ImageButton addToCartButton;

            ViewHolder(View itemView) {
                super(itemView);
                beverageName = itemView.findViewById(R.id.beverageNameMenu);
                beverageImage = itemView.findViewById(R.id.beverageImageMenu);
                priceTextView = itemView.findViewById(R.id.priceTextViewMenu);
                addToCartButton = itemView.findViewById(R.id.addToCartButton);
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
            holder.beverageName.setText(beverageNames[position]);
            holder.beverageImage.setImageResource(beverageImages[position]);
            holder.priceTextView.setText("Rp " + beveragePrices[position]);

            holder.addToCartButton.setOnClickListener(v ->
                    Toast.makeText(getContext(), "Ditambahkan: " + beverageNames[position], Toast.LENGTH_SHORT).show()
            );
        }

        @Override
        public int getItemCount() {
            return beverageNames.length;
        }
    }
}