package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

public class Perfect_square_trinomial extends Floating_button {

    private MaterialCardView theory_btn, quiz_btn, facto_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_square_trinomial);

        home_btn = findViewById(R.id.home_btn);
        menu2_btn = findViewById(R.id.menu2_btn);
        theory_btn = findViewById(R.id.theory_btn);
        quiz_btn = findViewById(R.id.quiz_btn);
        facto_btn = findViewById(R.id.facto_btn);

        home_btn.setOnClickListener(v -> goToActivity(BottomMenu.class));

        menu2_btn.setIcon(R.drawable.evaluatin2);
        menu2_btn.setOnClickListener(v -> goToActivity(Perfect_square_trinomial.class));

        theory_btn.setOnClickListener(v -> goToActivity(Perfect_square_trinomial_theory.class));
        quiz_btn.setOnClickListener(v -> goToActivity(Perfect_square_trinomial_quiz.class));
        facto_btn.setOnClickListener(v -> goToActivity(FactoFaster.class));
    }

    private void goSection(Class<?> activity){
        Intent intent = new Intent(this, activity);
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