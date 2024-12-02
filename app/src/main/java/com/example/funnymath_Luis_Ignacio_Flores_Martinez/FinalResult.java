package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FinalResult extends AppCompatActivity {

    private ImageView resultImage;
    private TextView resultMessage, resultText;
    private Button nextSectionButton, repeatQuizButton;
    private String quiz;
    private int score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_result);

        MediaPlayer congrats = MediaPlayer.create(FinalResult.this, R.raw.congrats);
        MediaPlayer failed = MediaPlayer.create(FinalResult.this, R.raw.failed);

        resultImage = findViewById(R.id.result_image);
        resultMessage = findViewById(R.id.result_message);
        resultText = findViewById(R.id.result);
        nextSectionButton = findViewById(R.id.nextSection);
        repeatQuizButton = findViewById(R.id.repeatQuiz);

        score = getIntent().getIntExtra("score", 0); // Obtener la puntuación
        quiz = getIntent().getStringExtra("quiz_name");
        resultText.setText(String.valueOf(score)); // Mostrar la puntuación

        nextSectionButton.setOnClickListener(v -> goSection(quiz, score, congrats));
        repeatQuizButton.setOnClickListener(v -> goSection(quiz, score, failed));

        // Configurar la vista según la puntuación
        if (score >= 7) { // Aprobar
            congrats.start();
            resultImage.setImageResource(R.drawable.gift); // Cambiar la imagen
            resultMessage.setText("¡Felicidades!");
            nextSectionButton.setVisibility(View.VISIBLE); // Mostrar botón "Siguiente sección"
            repeatQuizButton.setVisibility(View.GONE);
        } else { // Reprobar
            failed.start();
            resultImage.setImageResource(R.drawable.angry); // Cambiar la imagen
            resultMessage.setText("¡Puedes hacerlo mejor!");
            nextSectionButton.setVisibility(View.GONE);    // Ocultar botón "Siguiente sección"
            repeatQuizButton.setVisibility(View.VISIBLE);
        }
    }

    private void goSection(String quiz, int score, MediaPlayer sound) {
        sound.stop();
        sound.release();

        Intent intent;

        if (score >= 7) {
            intent = new Intent(FinalResult.this, Congrats.class);
            startActivity(intent);
            finish();
        } else {
            intent = new Intent(FinalResult.this, Plus_difference_quiz.class);
            startActivity(intent);
            finish();
        }


    }
}