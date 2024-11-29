package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Evaluating2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluating2);

        int score = getIntent().getIntExtra("score", 0); // Obtener la puntuación

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Evaluating2.this, Quiz_result.class);
            intent.putExtra("score", score); // Pasar la puntuación a la siguiente actividad
            startActivity(intent);
            finish(); // Cerrar EvaluatingActivity
        }, 5000); // 5 segundos
    }
}