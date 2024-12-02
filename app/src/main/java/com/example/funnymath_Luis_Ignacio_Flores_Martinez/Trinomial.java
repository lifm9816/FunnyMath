package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

public class Trinomial extends Floating_button {

    private MaterialCardView theory_btn, quiz_btn, factorMaster_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trinomial);

        home_btn = findViewById(R.id.home_btn);
        menu2_btn = findViewById(R.id.menu2_btn);
        theory_btn = findViewById(R.id.theory_btn);
        quiz_btn = findViewById(R.id.quiz_btn);
        factorMaster_btn = findViewById(R.id.factorMaster_btn);

        home_btn.setOnClickListener(v -> goToActivity(BottomMenu.class));

        menu2_btn.setIcon(R.drawable.evaluatin3);
        menu2_btn.setOnClickListener(v -> goToActivity(Trinomial.class));

        theory_btn.setOnClickListener(v -> goSection(Trinomial_theory.class));
        quiz_btn.setOnClickListener(v -> goSection(Trinomial_quiz.class));
        factorMaster_btn.setOnClickListener(v -> goSection(FactorMasterPro.class));
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