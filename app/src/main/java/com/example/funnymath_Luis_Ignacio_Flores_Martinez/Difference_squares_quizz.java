package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class Difference_squares_quizz extends AppCompatActivity {

    private RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4, radioGroup5,
            radioGroup6, radioGroup7, radioGroup8, radioGroup9, radioGroup10;
    private Button submitButton;
    private int score = 0;
    private MediaPlayer  correct, wrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difference_squares_quizz); // Asegúrate de que este sea el nombre correcto de tu layout

        SoundManager.getInstance().pauseMainSound();

        SoundManager.getInstance().playQuizSound(this, R.raw.quiz);

        correct = MediaPlayer.create(Difference_squares_quizz.this, R.raw.correct);
        wrong = MediaPlayer.create(Difference_squares_quizz.this, R.raw.wrong);

        initializeViews();
        setupSubmitButton();
        setupRadioGroupListeners();
    }

    private void setupRadioGroupListeners() {
        setupRadioGroupListener(radioGroup1, R.id.answer1c);
        setupRadioGroupListener(radioGroup2, R.id.answer2c);
        setupRadioGroupListener(radioGroup3, R.id.answer3a);
        setupRadioGroupListener(radioGroup4, R.id.answer4a);
        setupRadioGroupListener(radioGroup5, R.id.answer5b);
        setupRadioGroupListener(radioGroup6, R.id.answer6c);
        setupRadioGroupListener(radioGroup7, R.id.answer7a);
        setupRadioGroupListener(radioGroup8, R.id.answer8a);
        setupRadioGroupListener(radioGroup9, R.id.answer9a);
        setupRadioGroupListener(radioGroup10, R.id.answer10a);
    }

    private void setupRadioGroupListener(RadioGroup radioGroup, int correctAnswerId) {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == correctAnswerId) {
                playSound(R.raw.correct);
            } else {
                playSound(R.raw.wrong);
            }
        });
    }

    private void playSound(int soundResId) {
        MediaPlayer soundPlayer = MediaPlayer.create(this, soundResId);
        soundPlayer.setOnCompletionListener(MediaPlayer::release);
        soundPlayer.start();
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

        String quiz = "difference_squares";

        Intent intent = new Intent(Difference_squares_quizz.this, Evaluating2.class);
        intent.putExtra("score", score); // Pasar la puntuación a la siguiente actividad
        intent.putExtra("quiz_name", quiz);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop(){
        super.onStop();
        SoundManager.getInstance().stopQuizSound();
    }

}