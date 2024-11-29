package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class Difference_squares_quizz extends AppCompatActivity {

    private RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4, radioGroup5,
            radioGroup6, radioGroup7, radioGroup8, radioGroup9, radioGroup10;
    private Button submitButton;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difference_squares_quizz); // Asegúrate de que este sea el nombre correcto de tu layout

        initializeViews();
        setupSubmitButton();
    }

    private void initializeViews() {
        // RadioGroups
        radioGroup1 = findViewById(R.id.radioGroup1);
        radioGroup2 = findViewById(R.id.radioGroup2);
        radioGroup3 = findViewById(R.id.radioGroup3);
        radioGroup4 = findViewById(R.id.radioGroup4);
        radioGroup5 = findViewById(R.id.radioGroup5);
        radioGroup6 = findViewById(R.id.radioGroup6);
        radioGroup7 = findViewById(R.id.radioGroup7);
        radioGroup8 = findViewById(R.id.radioGroup8);
        radioGroup9 = findViewById(R.id.radioGroup9);
        radioGroup10 = findViewById(R.id.radioGroup10);

        submitButton = findViewById(R.id.submitButton);
    }

    private void setupSubmitButton() {
        submitButton.setOnClickListener(v -> checkAnswers());
    }

    private void checkAnswers() {
        score = 0;

        // Verificar pregunta 1 (RadioGroup)
        if (radioGroup1.getCheckedRadioButtonId() == R.id.answer1c) { // (2x + 7)(2x - 7)
            score++;
        }

        // Verificar pregunta 2 (RadioGroup)
        if (radioGroup2.getCheckedRadioButtonId() == R.id.answer2c) { // 16m⁴ - 81n²
            score++;
        }

        // Verificar pregunta 3 (RadioGroup)
        if (radioGroup3.getCheckedRadioButtonId() == R.id.answer3a) { // (10p³ + 1)(10p³ - 1)
            score++;
        }

        // Verificar pregunta 4 (RadioGroup)
        if (radioGroup4.getCheckedRadioButtonId() == R.id.answer4a) { // (x + 5)(x - 1)
            score++;
        }

        // Verificar pregunta 5 (RadioGroup)
        if (radioGroup5.getCheckedRadioButtonId() == R.id.answer5b) { // Longitud: x + y, Ancho: x - y
            score++;
        }

        // Verificar pregunta 6 (RadioGroup)
        if (radioGroup6.getCheckedRadioButtonId() == R.id.answer6c) { // (5x + 1)(5x - 1)
            score++;
        }

        // Verificar pregunta 7 (RadioGroup)
        if (radioGroup7.getCheckedRadioButtonId() == R.id.answer7a) { // (1 + 9y²)(1 - 9y²)
            score++;
        }

        // Verificar pregunta 8 (RadioGroup)
        if (radioGroup8.getCheckedRadioButtonId() == R.id.answer8a) { // (a² + 4)(a² - 4)
            score++;
        }

        // Verificar pregunta 9 (RadioGroup)
        if (radioGroup9.getCheckedRadioButtonId() == R.id.answer9a) { // 4x + 5y
            score++;
        }

        // Verificar pregunta 10 (RadioGroup)
        if (radioGroup10.getCheckedRadioButtonId() == R.id.answer10a) { // (x + 4)(x - 10)
            score++;
        }

        Intent intent = new Intent(Difference_squares_quizz.this, Evaluating2.class);
        intent.putExtra("score", score); // Pasar la puntuación a la siguiente actividad
        startActivity(intent);
        finish();
    }
}