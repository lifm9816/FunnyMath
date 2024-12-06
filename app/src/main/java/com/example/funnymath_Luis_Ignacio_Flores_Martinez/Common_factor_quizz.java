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

public class Common_factor_quizz extends Floating_button {

    private RadioGroup[] radioGroups; // Array para almacenar los RadioGroups
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
        setContentView(R.layout.activity_common_factor_quizz);

        submitButton = findViewById(R.id.submitButton);

        submitButton.setEnabled(false);

        home_btn = findViewById(R.id.home_btn);
        menu2_btn = findViewById(R.id.menu2_btn);

        home_btn.setOnClickListener(v -> goToActivity(BottomMenu.class));
        menu2_btn.setIcon(R.drawable.explaning);
        menu2_btn.setOnClickListener(v -> goToActivity(Common_factor.class));

        initializeViews();
        initializeQuestions();
        updateViews();
        setupRadioGroupListeners();
        checkAllQuestionsAnswered();
        setupSubmitButton();

        SoundManager.getInstance().pauseMainSound();
        SoundManager.getInstance().playQuizSound(this, R.raw.quiz);
        correct = MediaPlayer.create(this, R.raw.correct);
        wrong = MediaPlayer.create(this, R.raw.wrong);
    }

    private void initializeQuestions() {
        questions = new ArrayList<>();

        // Generar 10 preguntas de opción única
        for (int i = 0; i < 10; i++) {
            Question q = generateSingleChoiceQuestion();
            questions.add(q);
        }
    }

    private Question generateSingleChoiceQuestion() {
        int a = random.nextInt(10) + 1;
        int b = random.nextInt(5) + 1;
        int x = random.nextInt(3) + 1;
        int y = random.nextInt(3) + 1;

        String expression = String.format("%dx^%dy^%d + %dx^%dy^%d",
                a * b, x + 1, y, a * 2 * b, x, y);
        String correctAnswer = String.format("%dx^%dy^%d", b, x, y);

        ArrayList<String> options = generateOptions(correctAnswer);

        return new Question(
                "¿Cuál es el factor común en la expresión: " + expression + "?",
                options,
                correctAnswer
        );
    }

    private ArrayList<String> generateOptions(String correctAnswer) {
        ArrayList<String> options = new ArrayList<>();
        options.add(correctAnswer);

        String[] parts = correctAnswer.split("x\\^|y\\^");
        int coef = Integer.parseInt(parts[0]);
        int xExp = Integer.parseInt(parts[1]);
        int yExp = Integer.parseInt(parts[2]);

        // Generar opciones incorrectas modificando coeficientes y exponentes
        options.add(String.format("%dx^%dy^%d", coef + 1, xExp + 1, yExp));
        options.add(String.format("%dx^%dy^%d", coef, xExp + 1, yExp + 1));
        options.add(String.format("%dx^%dy^%d", coef - 1, xExp, yExp - 1));

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
            Log.e("Common_factor_quizz", "RadioGroup es null");
        }
    }

    private void updateViews() {
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            TextView questionText = findViewById(getResources().getIdentifier(
                    "question" + (i + 1), "id", getPackageName()));
            questionText.setText(q.question);

            RadioGroup rg = radioGroups[i]; // Obtener el RadioGroup del array
            updateRadioButtons(rg, q.options);
        }
    }

    private void initializeViews() {
        radioGroups = new RadioGroup[10]; // Inicializar el array de RadioGroups

        // Obtener referencias a los RadioGroups del layout
        for (int i = 0; i < 10; i++) {
            radioGroups[i] = findViewById(getResources().getIdentifier(
                    "radioGroup" + (i + 1), "id", getPackageName()));
        }
        for (RadioGroup rg : radioGroups) {
            rg.setOnCheckedChangeListener((group, checkedId) -> {
                checkAllQuestionsAnswered();
            });
        }
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
        submitButton.setOnClickListener(v -> {
            checkAnswers();
        });
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

        String quiz = "common_factor";
        Intent intent = new Intent(Common_factor_quizz.this, Evaluating.class);
        intent.putExtra("score", score);
        intent.putExtra("quiz_name", quiz);
        startActivity(intent);
        finish();
    }

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

    @Override
    protected void onStop(){
        super.onStop();
        SoundManager.getInstance().stopQuizSound();
    }
}