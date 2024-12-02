package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.app.Dialog;
import android.content.Intent;

public class FactoFaster extends Floating_button {
    private TextView tvProgress, tvScore, tvExercise, tvFeedback;
    private EditText etAnswer;
    private Button btnCheck;
    private FactoMasterGame game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facto_faster);

        home_btn = findViewById(R.id.home_btn);
        menu2_btn = findViewById(R.id.menu2_btn);

        home_btn.setOnClickListener(v -> goToActivity(BottomMenu.class));

        menu2_btn.setIcon(R.drawable.evaluatin2);
        menu2_btn.setOnClickListener(v -> goToActivity(Perfect_square_trinomial.class));

        SoundManager.getInstance().pauseMainSound();
        SoundManager.getInstance().playQuizSound(this ,R.raw.game);

        // Initialize views
        initializeViews();

        // Initialize game
        game = new FactoMasterGame();
        updateUI();

        // Set up check button
        btnCheck.setOnClickListener(v -> checkAnswer());
    }

    private void initializeViews() {
        tvProgress = findViewById(R.id.tvProgress);
        tvScore = findViewById(R.id.tvScore);
        tvExercise = findViewById(R.id.tvExercise);
        tvFeedback = findViewById(R.id.tvFeedback);
        etAnswer = findViewById(R.id.etAnswer);
        btnCheck = findViewById(R.id.btnCheck);
    }

    private void checkAnswer() {
        String answer = etAnswer.getText().toString().trim();
        if (answer.isEmpty()) {
            Toast.makeText(this, "Por favor teclee su respuesta", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isCorrect = game.checkAnswer(answer);
        if (isCorrect) {
            tvFeedback.setText("Correcto!");
            if (game.isGameFinished()) {
                finishGame();
            } else {
                game.nextExercise();
                etAnswer.setText("");
                updateUI();
            }
        } else {
            tvFeedback.setText("Incorrecto. La respuesta correcta es: " + game.getCorrectAnswer());
        }
    }

    private void updateUI() {
        tvProgress.setText("Ejercicio " + game.getCurrentExerciseNumber() + " of 7");
        tvScore.setText("Puntuación: " + game.getScore());
        tvExercise.setText(game.getCurrentExercise());
        tvFeedback.setText("");
    }

    private void finishGame() {
        // Create dialog
        Dialog winnerDialog = new Dialog(this);
        winnerDialog.setContentView(R.layout.facto_winner);
        winnerDialog.setCancelable(false);

        // Get dialog views
        TextView tvFinalScore = winnerDialog.findViewById(R.id.tvFinalScore);
        Button btnNextQuiz = winnerDialog.findViewById(R.id.btnNextQuiz);
        Button btnReviewTheory = winnerDialog.findViewById(R.id.btnReviewTheory);

        // Set final score
        tvFinalScore.setText("Final Score: " + game.getScore() + "/70");

        // Set button click listeners
        btnNextQuiz.setOnClickListener(v -> {
            // Add your quiz activity intent here
            Intent quizIntent = new Intent(FactoFaster.this, Perfect_square_trinomial_quiz.class);
            startActivity(quizIntent);
            finish();
        });

        btnReviewTheory.setOnClickListener(v -> {
            // Add your theory activity intent here
            Intent theoryIntent = new Intent(FactoFaster.this, Perfect_square_trinomial_theory.class);
            startActivity(theoryIntent);
            finish();
        });

        // Show dialog
        winnerDialog.show();

        // Disable game controls
        btnCheck.setEnabled(false);
        etAnswer.setEnabled(false);
    }

    @Override
    protected void onStop(){
        super.onStop();
        SoundManager.getInstance().stopQuizSound();
    }
}