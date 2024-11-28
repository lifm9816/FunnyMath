package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
// ... otras importaciones

public class Quiz_result extends AppCompatActivity {

    private ImageView resultImage;
    private TextView resultMessage, resultText;
    private Button nextSectionButton, repeatQuizButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        resultImage = findViewById(R.id.result_image);
        resultMessage = findViewById(R.id.result_message);
        resultText = findViewById(R.id.result);
        nextSectionButton = findViewById(R.id.nextSection);
        repeatQuizButton = findViewById(R.id.repeatQuiz);

        int score = getIntent().getIntExtra("score", 0); // Obtener la puntuación
        resultText.setText(String.valueOf(score)); // Mostrar la puntuación

        // Configurar la vista según la puntuación
        if (score >= 7) { // Aprobar
            resultImage.setImageResource(R.drawable.heart); // Cambiar la imagen
            resultMessage.setText("¡Felicidades!");
            nextSectionButton.setEnabled(true);
            repeatQuizButton.setEnabled(false);
        } else { // Reprobar
            resultImage.setImageResource(R.drawable.sad); // Cambiar la imagen
            resultMessage.setText("¡Puedes hacerlo mejor!");
            nextSectionButton.setEnabled(false);
            repeatQuizButton.setEnabled(true);
        }
    }
}