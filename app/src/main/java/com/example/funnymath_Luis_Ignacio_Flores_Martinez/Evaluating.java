package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Evaluating extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluating);
        MediaPlayer evaluating = MediaPlayer.create(Evaluating.this, R.raw.evaluating);
        evaluating.start();

        int score = getIntent().getIntExtra("score", 0); // Obtener la puntuación
        String quiz = getIntent().getStringExtra("quiz_name");

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Evaluating.this, Quiz_result.class);
            intent.putExtra("score", score); // Pasar la puntuación a la siguiente actividad
            intent.putExtra("quiz_name", quiz);
            evaluating.stop();
            startActivity(intent);
            finish(); // Cerrar EvaluatingActivity
        }, 5000); // 5 segundos

    }
}