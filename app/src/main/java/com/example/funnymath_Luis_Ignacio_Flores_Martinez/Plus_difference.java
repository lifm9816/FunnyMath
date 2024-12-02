package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

public class Plus_difference extends Floating_button {

    private MaterialCardView theory_btn, quiz_btn, cubeFactor_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus_difference);

        home_btn = findViewById(R.id.home_btn);
        menu2_btn = findViewById(R.id.menu2_btn);
        theory_btn = findViewById(R.id.theory_btn);
        quiz_btn = findViewById(R.id.quiz_btn);
        cubeFactor_btn = findViewById(R.id.cubeFactor_btn);

        home_btn.setOnClickListener(v -> goToActivity(BottomMenu.class));

        menu2_btn.setIcon(R.drawable.teaching);
        menu2_btn.setOnClickListener(v -> goToActivity(Plus_difference.class));

        theory_btn.setOnClickListener(v -> goSection(Plus_difference_theory.class));
        quiz_btn.setOnClickListener(v -> goSection(Plus_difference_quiz.class));
        cubeFactor_btn.setOnClickListener(v -> goSection(CubeFactor.class));
    }

    public void goSection(Class<?> activity)
    {
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