package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

public class Difference_squares extends Floating_button {

    private MaterialCardView memorama_btn, theory_btn, quiz_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difference_squares);

        home_btn = findViewById(R.id.home_btn);
        menu2_btn = findViewById(R.id.menu2_btn);
        memorama_btn = findViewById(R.id.memorama_btn);
        theory_btn = findViewById(R.id.theory_btn);
        quiz_btn = findViewById(R.id.quiz_btn);

        theory_btn.setOnClickListener(v -> goToActivity(Difference_squares_theory.class));

        quiz_btn.setOnClickListener(v -> goToActivity(Difference_squares_quizz.class));

        memorama_btn.setOnClickListener(v -> goToSection(Memorama.class));

        home_btn.setOnClickListener(v -> goToActivity(BottomMenu.class));

        menu2_btn.setIcon(R.drawable.evaluating);
        menu2_btn.setOnClickListener(v -> goToActivity(Difference_squares.class));
    }

    private void goToSection(Class<?> activity){
        Intent intent = new Intent(Difference_squares.this, activity);
        startActivity(intent);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(!SoundManager.getInstance().isMainSoundPlaying()){
            SoundManager.getInstance().playMainSound(this, R.raw.gamemenu);
        }
    }
}