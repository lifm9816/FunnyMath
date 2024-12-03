package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FactorMasterPro extends Floating_button {
    private TextView tvLevel, tvScore, tvExpression, tvFeedback;
    private EditText etFactors, etSum1, etSum2, etFactored;
    private Button btnHint1, btnHint2, btnHint3, btnCheck;
    private FactorMasterProGame game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factor_master_pro);

        SoundManager.getInstance().pauseMainSound();
        SoundManager.getInstance().playQuizSound(this, R.raw.game2);

        home_btn = findViewById(R.id.home_btn);
        menu2_btn = findViewById(R.id.menu2_btn);

        home_btn.setOnClickListener(v -> goToActivity(BottomMenu.class));

        menu2_btn.setIcon(R.drawable.evaluatin3);
        menu2_btn.setOnClickListener(v -> goToActivity(Trinomial.class));

        initializeViews();
        setupGame();
        setupListeners();
    }

    private void initializeViews() {
        tvLevel = findViewById(R.id.tvLevel);
        tvScore = findViewById(R.id.tvScore);
        tvExpression = findViewById(R.id.tvExpression);
        tvFeedback = findViewById(R.id.tvFeedback);
        etFactors = findViewById(R.id.etFactors);
        etSum1 = findViewById(R.id.etSum1);
        etSum2 = findViewById(R.id.etSum2);
        etFactored = findViewById(R.id.etFactored);
        btnHint1 = findViewById(R.id.btnHint1);
        btnHint2 = findViewById(R.id.btnHint2);
        btnHint3 = findViewById(R.id.btnHint3);
        btnCheck = findViewById(R.id.btnCheck);
    }

    private void setupGame() {
        game = new FactorMasterProGame();
        updateUI();
    }

    private void setupListeners() {
        btnHint1.setOnClickListener(v -> {
            game.useHint();
            tvFeedback.setText(game.getCurrentExpression().getFactorsHint());
        });

        btnHint2.setOnClickListener(v -> {
            game.useHint();
            tvFeedback.setText(game.getCurrentExpression().getSumsHint());
        });

        btnHint3.setOnClickListener(v -> {
            game.useHint();
            tvFeedback.setText(game.getCurrentExpression().getFactoredHint());
        });

        btnCheck.setOnClickListener(v -> checkAnswer());
    }

    private void checkAnswer() {
        FactorMasterProGame.QuadraticExpression current = game.getCurrentExpression();
        boolean step1Correct = false, step2Correct = false, step3Correct = false;
        StringBuilder feedback = new StringBuilder();

        // Check Step 1
        try {
            step1Correct = current.checkFactors(etFactors.getText().toString());
            if (!step1Correct) {
                feedback.append("Paso 1: Factores incorrectos\n");
            }
        } catch (Exception e) {
            feedback.append("Step 1: Formato de entrada no válido\n");
        }

        // Check Step 2
        try {
            int sum1 = Integer.parseInt(etSum1.getText().toString());
            int sum2 = Integer.parseInt(etSum2.getText().toString());
            step2Correct = current.checkSums(sum1, sum2);
            if (!step2Correct) {
                feedback.append("Paso 2: Los números no suman correctamente\n");
            }
        } catch (Exception e) {
            feedback.append("Paso 2: Números inválidos\n");
        }

        // Check Step 3
        step3Correct = current.checkFactored(etFactored.getText().toString());
        if (!step3Correct) {
            feedback.append("Paso 3: Factorización incorrecta\n");
        }

        // Calculate score and proceed
        if (step1Correct && step2Correct && step3Correct) {
            int pointsEarned = 30 - (game.getHintsUsed() * 5);
            game.addScore(Math.max(10, pointsEarned));

            if (game.isGameFinished()) {
                showWinnerDialog();
            } else {
                game.nextLevel();
                clearInputs();
                updateUI();
                tvFeedback.setText("Correcto! +" + pointsEarned + " puntos");
            }
        } else {
            tvFeedback.setText(feedback.toString());
        }
    }

    private void clearInputs() {
        etFactors.setText("");
        etSum1.setText("");
        etSum2.setText("");
        etFactored.setText("");
    }

    private void updateUI() {
        tvLevel.setText("Nivel: " + game.getCurrentLevel());
        tvScore.setText("Puntuación: " + game.getScore());
        tvExpression.setText(game.getCurrentExpression().getExpression());
        tvFeedback.setText("");
    }

    private void showWinnerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Felicidades!")
                .setMessage("Haz completado todos los niveles!\nPuntuación final: " + game.getScore())
                .setPositiveButton("Jugar de nuevo", (dialog, which) -> {
                    game = new FactorMasterProGame();
                    clearInputs();
                    updateUI();
                })
                .setNegativeButton("Salir", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save game state
        outState.putInt("currentLevel", game.getCurrentLevel());
        outState.putInt("score", game.getScore());
        outState.putInt("hintsUsed", game.getHintsUsed());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore game state
        if (savedInstanceState != null) {
            game.setCurrentLevel(savedInstanceState.getInt("currentLevel"));
            game.setScore(savedInstanceState.getInt("score"));
            game.setHintsUsed(savedInstanceState.getInt("hintsUsed"));
            updateUI();
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        SoundManager.getInstance().stopQuizSound();
    }
}