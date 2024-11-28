package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.card.MaterialCardView;

public class Common_factor extends Floating_button {

    private MaterialCardView theory_btn, quiz_btn, puzzle_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_factor);

        home_btn = findViewById(R.id.home_btn);
        menu2_btn = findViewById(R.id.menu2_btn);
        theory_btn = findViewById(R.id.theory_btn);
        quiz_btn = findViewById(R.id.quiz_btn);
        puzzle_btn = findViewById(R.id.puzzle_btn);

        home_btn.setOnClickListener(v -> goToActivity(BottomMenu.class));

        menu2_btn.setIcon(R.drawable.explaning);
        menu2_btn.setOnClickListener(v -> goToActivity(Common_factor.class));

        theory_btn.setOnClickListener(v -> goSection(Common_factor_theory.class));

        quiz_btn.setOnClickListener(v -> goSection(Common_factor_quizz.class));

        puzzle_btn.setOnClickListener(v -> goSection(Common_factor_puzzle.class));
    }

    private void goSection(Class<?> activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}