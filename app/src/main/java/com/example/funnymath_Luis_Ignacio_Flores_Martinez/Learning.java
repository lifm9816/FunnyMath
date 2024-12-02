package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;

public class Learning extends Fragment {

    private MaterialCardView common_factor, difference_squares, perfect_square_trinomial, trinomial, plus_difference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_learning, container, false);

        common_factor = view.findViewById(R.id.common_factor);
        difference_squares = view.findViewById(R.id.difference_squares);
        perfect_square_trinomial = view.findViewById(R.id.perfect_square_trinomial);
        trinomial = view.findViewById(R.id.trinomial);
        plus_difference = view.findViewById(R.id.plus_difference);

        common_factor.setOnClickListener(v -> goSection(Common_factor.class));

        difference_squares.setOnClickListener(v -> goSection(Difference_squares.class));

        perfect_square_trinomial.setOnClickListener(v -> goSection(Perfect_square_trinomial.class));

        trinomial.setOnClickListener(v -> goSection(Trinomial.class));

        plus_difference.setOnClickListener(v -> goSection(Plus_difference.class));

        return view;
    }

    private void goSection(Class<?> section){
        Intent intent = new Intent(requireContext(), section);
        startActivity(intent);
    }
}