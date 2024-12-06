package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Perfect_square_trinomial_quiz extends Floating_button {

    private RadioGroup[] radioGroups;
    private Button submitButton;
    private int score = 0;
    private MediaPlayer correct, wrong;
    private Random random = new Random();
    private ArrayList<Question> questions;

    private class Question {
        String question;
        ArrayList<String> options;
        String correctAnswer;

        Question(String question, ArrayList<String> options, String correctAnswer) {
            this.question = question;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_square_trinomial_quiz);

        home_btn = findViewById(R.id.home_btn);
        menu2_btn = findViewById(R.id.menu2_btn);

        home_btn.setOnClickListener(v -> goToActivity(BottomMenu.class));
        menu2_btn.setIcon(R.drawable.evaluatin2);
        menu2_btn.setOnClickListener(v -> goToActivity(Perfect_square_trinomial.class));

        initializeViews();
        initializeQuestions();
        updateViews();
        setupRadioGroupListeners();
        setupSubmitButton();

        SoundManager.getInstance().pauseMainSound();
        SoundManager.getInstance().playQuizSound(this, R.raw.quiz);
        correct = MediaPlayer.create(this, R.raw.correct);
        wrong = MediaPlayer.create(this, R.raw.wrong);
    }

    private void initializeQuestions() {
        questions = new ArrayList<>();

        // Generar 10 preguntas de trinomio cuadrado perfecto
        for (int i = 0; i < 10; i++) {
            Question q = generatePerfectSquareTrinomialQuestion();
            questions.add(q);
        }
    }

    private Question generatePerfectSquareTrinomialQuestion() {
        int a = random.nextInt(10) + 1; // Coeficiente del primer término
        int b = random.nextInt(10) + 1; // Coeficiente del segundo término
        int x = random.nextInt(3) + 1;  // Exponente del primer término

        // Calcular el término independiente para que sea un trinomio cuadrado perfecto
        int c = a * b * 2;

        String expression = String.format("%d^%d + %dx^%d + %d", a*a, 2*x, c, x, b*b);
        String correctAnswer = String.format("(%d^%d + %d)^2", a, x, b);

        ArrayList<String> options = generateOptions(correctAnswer, a, b, x);

        return new Question(
                "¿Cuál es la factorización de la expresión: " + expression + "?",
                options,
                correctAnswer
        );
    }

    private ArrayList<String> generateOptions(String correctAnswer, int a, int b, int x) {
        ArrayList<String> options = new ArrayList<>();
        options.add(correctAnswer);

        // Generar opciones incorrectas
        options.add(String.format("(%d^%d - %d)^2", a, x, b));
        options.add(String.format("(%d^%d + %d)(%d^%d - %d)", a, x, b, a, x, b));
        options.add(String.format("(%d^%d + %d)^2", a+1, x, b));

        Collections.shuffle(options);
        return options;
    }

    private void updateRadioButtons(RadioGroup rg, ArrayList<String> options) {
        if (rg != null) {
            rg.removeAllViews();
            for (int i = 0; i < options.size(); i++) {
                RadioButton rb = new RadioButton(this);
                rb.setText(options.get(i));
                rb.setId(View.generateViewId());
                rg.addView(rb);
            }
        } else {
            Log.e("Perfect_square_trinomial_quiz", "RadioGroup es null");
        }
    }

    private void updateViews() {
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            TextView questionText = findViewById(getResources().getIdentifier(
                    "question" + (i + 1), "id", getPackageName()));
            if (questionText != null) {
                questionText.setText(q.question);
            } else {
                Log.e("Perfect_square_trinomial_quiz", "TextView question" + (i + 1) + " es null");
            }

            RadioGroup rg = radioGroups[i];
            updateRadioButtons(rg, q.options);
        }
    }

    private void initializeViews() {
        radioGroups = new RadioGroup[10];

        for (int i = 0; i < 10; i++) {
            radioGroups[i] = findViewById(getResources().getIdentifier(
                    "radioGroup" + (i + 1), "id", getPackageName()));
        }

        submitButton = findViewById(R.id.submitButton);
        submitButton.setEnabled(false); // Deshabilitar el botón al inicio
    }

    private void checkAllQuestionsAnswered() {
        boolean allAnswered = true;
        for (RadioGroup rg : radioGroups) {
            if (rg.getCheckedRadioButtonId() == -1) {
                allAnswered = false;
                break;
            }
        }
        submitButton.setEnabled(allAnswered);
    }

    private void setupSubmitButton() {
        submitButton.setOnClickListener(v -> checkAnswers());

        // Agregar OnCheckedChangeListener a cada RadioGroup
        for (RadioGroup rg : radioGroups) {
            rg.setOnCheckedChangeListener((group, checkedId) -> {
                checkAllQuestionsAnswered();
            });
        }
    }

    private void checkAnswers() {
        score = 0;

        for (int i = 0; i < questions.size(); i++) {
            RadioGroup rg = radioGroups[i];
            Question q = questions.get(i);

            int selectedId = rg.getCheckedRadioButtonId();
            if (selectedId != -1) {
                RadioButton selectedRadioButton = findViewById(selectedId);
                String selectedAnswer = selectedRadioButton.getText().toString();
                if (selectedAnswer.equals(q.correctAnswer)) {
                    score++;
                }
            }
        }

        String quiz = "perfect_square_trinomial";
        Intent intent = new Intent(Perfect_square_trinomial_quiz.this, Evaluating2.class);
        intent.putExtra("score", score);
        intent.putExtra("quiz_name", quiz);
        startActivity(intent);
        finish();
    }

    // ... (código anterior) ...

    private void setupRadioGroupListeners() {
        for (int i = 0; i < radioGroups.length; i++) {
            final int questionIndex = i; // Necesario para usar dentro del listener
            radioGroups[i].setOnCheckedChangeListener((group, checkedId) -> {
                checkAnswer(questionIndex, checkedId); // Llamar a checkAnswer con el índice de la pregunta
                checkAllQuestionsAnswered();
            });
        }
    }

    private void checkAnswer(int questionIndex, int checkedId) {
        Question q = questions.get(questionIndex);
        RadioGroup rg = radioGroups[questionIndex];

        int selectedId = rg.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedId);
            String selectedAnswer = selectedRadioButton.getText().toString();
            if (selectedAnswer.equals(q.correctAnswer)) {
                playSound(R.raw.correct);
            } else {
                playSound(R.raw.wrong);
            }
        }
    }

    private void playSound(int soundResId) {
        MediaPlayer soundPlayer = MediaPlayer.create(this, soundResId);
        soundPlayer.setOnCompletionListener(MediaPlayer::release);
        soundPlayer.start();
    }

// ... (resto del código) ...

    @Override
    protected void onStop(){
        super.onStop();
        SoundManager.getInstance().stopQuizSound();
    }
}