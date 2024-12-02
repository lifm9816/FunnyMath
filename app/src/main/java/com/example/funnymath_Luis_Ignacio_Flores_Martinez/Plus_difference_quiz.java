package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Plus_difference_quiz extends AppCompatActivity {

    private RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4, radioGroup5,
            radioGroup6, radioGroup7, radioGroup8, radioGroup9, radioGroup10;
    private Button submitButton;
    private int score = 0;
    private MediaPlayer correct, wrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plus_difference_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SoundManager.getInstance().pauseMainSound();
        SoundManager.getInstance().playQuizSound(this, R.raw.quiz);

        correct = MediaPlayer.create(Plus_difference_quiz.this, R.raw.correct);
        wrong = MediaPlayer.create(Plus_difference_quiz.this, R.raw.wrong);

        initializeViews();
        setupSubmitButton();
        setupRadioGroupListeners();
    }

    private void setupRadioGroupListeners() {
        // Aquí debes configurar las respuestas correctas para cada RadioGroup
        setupRadioGroupListener(radioGroup1, R.id.answer1a);
        setupRadioGroupListener(radioGroup2, R.id.answer2a);
        setupRadioGroupListener(radioGroup3, R.id.answer3a);
        setupRadioGroupListener(radioGroup4, R.id.answer4a);
        setupRadioGroupListener(radioGroup5, R.id.answer5c);
        setupRadioGroupListener(radioGroup6, R.id.answer6a);
        setupRadioGroupListener(radioGroup7, R.id.answer7b);
        setupRadioGroupListener(radioGroup8, R.id.answer8a);
        setupRadioGroupListener(radioGroup9, R.id.answer9a);
        setupRadioGroupListener(radioGroup10, R.id.answer10a);
    }

    private void setupRadioGroupListener(RadioGroup radioGroup, int correctAnswerId) {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == correctAnswerId) {
                playSound(R.raw.correct); // Reproduce sonido de respuesta correcta
            } else {
                playSound(R.raw.wrong);   // Reproduce sonido de respuesta incorrecta
            }
        });
    }

    private void playSound(int soundResId) {
        MediaPlayer soundPlayer = MediaPlayer.create(this, soundResId);
        soundPlayer.setOnCompletionListener(MediaPlayer::release);
        soundPlayer.start();
    }

    private void initializeViews() {
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

        if (radioGroup1.getCheckedRadioButtonId() == R.id.answer1a) score++;
        if (radioGroup2.getCheckedRadioButtonId() == R.id.answer2a) score++;
        if (radioGroup3.getCheckedRadioButtonId() == R.id.answer3a) score++;
        if (radioGroup4.getCheckedRadioButtonId() == R.id.answer4a) score++;
        if (radioGroup5.getCheckedRadioButtonId() == R.id.answer5c) score++;
        if (radioGroup6.getCheckedRadioButtonId() == R.id.answer6a) score++;
        if (radioGroup7.getCheckedRadioButtonId() == R.id.answer7b) score++;
        if (radioGroup8.getCheckedRadioButtonId() == R.id.answer8a) score++;
        if (radioGroup9.getCheckedRadioButtonId() == R.id.answer9a) score++;
        if (radioGroup10.getCheckedRadioButtonId() == R.id.answer10a) score++;

        String quiz = "sum_difference_cubes";

        Intent intent = new Intent(Plus_difference_quiz.this, EvaluatingFinal.class);
        intent.putExtra("score", score);
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