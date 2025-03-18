package com.example.kasir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.kasir.databinding.FregmentMinumanBinding;

public class MinumanFragment extends Fragment {
    private @NonNull FregmentMinumanBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inisialisasi ViewBinding
        binding = FregmentMinumanBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Mengakses GridView dari ViewBinding
        GridView gridView = binding.gridView;

        // TODO: Tambahkan adapter dan data untuk menampilkan daftar minuman
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Menghindari memory leak
    }
}