package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Trinomial_theory extends Floating_button {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trinomial_theory);

        home_btn = findViewById(R.id.home_btn);
        menu2_btn = findViewById(R.id.menu2_btn);

        home_btn.setOnClickListener(v -> goToActivity(BottomMenu.class));

        menu2_btn.setIcon(R.drawable.evaluatin3);
        menu2_btn.setOnClickListener(v -> goToActivity(Trinomial.class));

        SoundManager.getInstance().pauseMainSound();
        SoundManager.getInstance().playQuizSound(this, R.raw.quiz);
    }

    @Override
    protected void onStop(){
        super.onStop();
        SoundManager.getInstance().stopQuizSound();
    }
}