package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CubeFactor extends Floating_button {
    private CubeFactorGame game;
    private TextView tvProblem;
    private TextView tvFeedback;
    private TextView tvScore;
    private EditText etFirstTerm;
    private EditText etSecondTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cube_factor);

        // Inicializar elementos de Floating_button
        home_btn = findViewById(R.id.home_btn);
        menu2_btn = findViewById(R.id.menu2_btn);

        home_btn.setOnClickListener(v -> goToActivity(BottomMenu.class));
        menu2_btn.setIcon(R.drawable.teaching);
        menu2_btn.setOnClickListener(v -> goToActivity(Plus_difference.class));

        // Gestión del sonido
        SoundManager.getInstance().pauseMainSound();
        SoundManager.getInstance().playQuizSound(this, R.raw.game);

        // Inicializar el juego - asegurarse que se crea la instancia
        game = new CubeFactorGame();

        // Inicializar vistas
        initializeViews();

        // Configurar listeners
        setupListeners();

        // Generar primer problema y mostrar score inicial
        generateNewProblem();
        updateScore();
    }

    private void initializeViews() {
        tvProblem = findViewById(R.id.tvProblem);
        tvFeedback = findViewById(R.id.tvFeedback);
        tvScore = findViewById(R.id.tvScore);
        etFirstTerm = findViewById(R.id.etFirstTerm);
        etSecondTerm = findViewById(R.id.etSecondTerm);
    }

    private void setupListeners() {
        Button btnCheck = findViewById(R.id.btnCheck);
        Button btnNewProblem = findViewById(R.id.btnNewProblem);
        Button btnHint = findViewById(R.id.btnHint);

        btnCheck.setOnClickListener(v -> checkAnswer());
        btnNewProblem.setOnClickListener(v -> generateNewProblem());
        btnHint.setOnClickListener(v -> showHint());
    }

    private void generateNewProblem() {
        if (game != null) {
            String problem = game.generateProblem();
            tvProblem.setText(problem);
            etFirstTerm.setText("");
            etSecondTerm.setText("");
            tvFeedback.setText("");
        }
    }

    private void checkAnswer() {
        String firstTerm = etFirstTerm.getText().toString();
        String secondTerm = etSecondTerm.getText().toString();

        if (firstTerm.isEmpty() || secondTerm.isEmpty()) {
            tvFeedback.setText("Por favor completa ambos términos");
            return;
        }

        if (game.checkAnswer(firstTerm, secondTerm)) {
            tvFeedback.setText("¡Correcto! ¡Muy bien!");
            updateScore();
        } else {
            tvFeedback.setText("Incorrecto. La respuesta correcta es: " + game.getCorrectAnswer());
        }
    }

    private void showHint() {
        String hint = game.getHint();
        new AlertDialog.Builder(this)
                .setTitle("Pista")
                .setMessage(hint)
                .setPositiveButton("Entendido", null)
                .show();
    }

    private void updateScore() {
        tvScore.setText(String.format("Puntaje: %d", game.getScore()));
    }

    @Override
    protected void onStop() {
        super.onStop();
        SoundManager.getInstance().stopQuizSound();
    }
}