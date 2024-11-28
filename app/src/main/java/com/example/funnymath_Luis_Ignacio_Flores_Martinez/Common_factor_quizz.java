package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Common_factor_quizz extends AppCompatActivity {

    private RadioGroup radioGroup1, radioGroup3, radioGroup4, radioGroup6, radioGroup8, radioGroup10;
    private CheckBox[] question2Checks, question5Checks, question7Checks, question9Checks;
    private Button submitButton;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_factor_puzzle);

        initializeViews();
        setupSubmitButton();
    }

    private void initializeViews() {
        // RadioGroups
        radioGroup1 = findViewById(R.id.radioGroup1);
        radioGroup3 = findViewById(R.id.radioGroup3);
        radioGroup4 = findViewById(R.id.radioGroup4);
        radioGroup6 = findViewById(R.id.radioGroup6);
        radioGroup8 = findViewById(R.id.radioGroup8);
        radioGroup10 = findViewById(R.id.radioGroup10);

        // CheckBoxes para pregunta 2
        question2Checks = new CheckBox[]{
                findViewById(R.id.check2_1),
                findViewById(R.id.check2_2),
                findViewById(R.id.check2_3),
                findViewById(R.id.check2_4)
        };

        // CheckBoxes para pregunta 5
        question5Checks = new CheckBox[]{
                findViewById(R.id.check5_1),
                findViewById(R.id.check5_2),
                findViewById(R.id.check5_3),
                findViewById(R.id.check5_4)
        };

        // ... inicializar otros CheckBoxes

        submitButton = findViewById(R.id.submitButton);
    }

    private void setupSubmitButton() {
        submitButton.setOnClickListener(v -> checkAnswers());
    }

    private void checkAnswers() {
        score = 0;

        // Verificar pregunta 1 (RadioGroup)
        if (radioGroup1.getCheckedRadioButtonId() == R.id.radio1_1) { // 5xy
            score++;
        }

        // Verificar pregunta 2 (CheckBoxes)
        boolean q2correct = question2Checks[1].isChecked() &&
                question2Checks[3].isChecked() &&
                !question2Checks[0].isChecked() &&
                !question2Checks[2].isChecked();
        if (q2correct) score++;

        // Verificar pregunta 3 (RadioGroup)
        if (radioGroup3.getCheckedRadioButtonId() == R.id.radio3_1) { // 6x²y²
            score++;
        }

        // Verificar pregunta 4 (RadioGroup)
        if (radioGroup4.getCheckedRadioButtonId() == R.id.radio4_2) { // 4x³
            score++;
        }

        // Verificar pregunta 5 (CheckBoxes)
        boolean q5correct = question5Checks[0].isChecked() &&
                question5Checks[1].isChecked() &&
                !question5Checks[2].isChecked() &&
                question5Checks[3].isChecked();
        if (q5correct) score++;


        if (radioGroup6.getCheckedRadioButtonId() == R.id.radio6_1) { // 5x²y²
            score++;
        }

        // Verificar pregunta 7 (CheckBoxes)
        boolean q7correct = question7Checks[0].isChecked() &&
                !question7Checks[1].isChecked() &&
                question7Checks[2].isChecked() &&
                question7Checks[3].isChecked();
        if (q7correct) score++;

        // Verificar pregunta 8 (RadioGroup)
        if (radioGroup8.getCheckedRadioButtonId() == R.id.radio8_1) { // 3xy²
            score++;
        }

        // Verificar pregunta 9 (CheckBoxes)
        boolean q9correct = question9Checks[0].isChecked() &&
                question9Checks[1].isChecked() &&
                !question9Checks[2].isChecked() &&
                question9Checks[3].isChecked();
        if (q9correct) score++;

        // Verificar pregunta 10 (RadioGroup)
        if (radioGroup10.getCheckedRadioButtonId() == R.id.radio10_1) { // 5x²y²
            score++;
        }

        Intent intent = new Intent(Common_factor_quizz.this, Evaluating.class);
        intent.putExtra("score", score); // Pasar la puntuación a la siguiente actividad
        startActivity(intent);
    }
}