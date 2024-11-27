package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;

public class Learning extends Fragment {

    private MaterialCardView common_factor, difference_squares;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_learning, container, false);

        common_factor = view.findViewById(R.id.common_factor);
        difference_squares = view.findViewById(R.id.difference_squares);

        common_factor.setOnClickListener(v -> goSection(Common_factor.class));

        return view;
    }

    private void goSection(Class<?> section){
        Intent intent = new Intent(requireContext(), section);
        startActivity(intent);
    }
}