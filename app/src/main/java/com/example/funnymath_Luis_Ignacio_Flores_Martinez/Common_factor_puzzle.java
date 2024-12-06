package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Common_factor_puzzle extends Floating_button {

    private TextView[] dropZones;
    private TextView[] puzzlePieces;
    private float dX, dY;
    private int activePointerId;
    private TextView selectedPiece = null;
    private float[] originalX, originalY;
    private Random random = new Random();
    private int a1, b1, x1, a2, b2, x2; // Variables de instancia para los ejercicios

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_factor_puzzle);

        SoundManager.getInstance().pauseMainSound();
        SoundManager.getInstance().playQuizSound(this, R.raw.game);

        home_btn = findViewById(R.id.home_btn);
        menu2_btn = findViewById(R.id.menu2_btn);

        home_btn.setOnClickListener(v -> goToActivity(BottomMenu.class));
        menu2_btn.setIcon(R.drawable.explaning);
        menu2_btn.setOnClickListener(v -> goToActivity(Common_factor.class));

        initializeViews();
        setupDragAndDrop();
        setupCheckButton();
        setupResetButton();
        saveOriginalPositions();
        showInstructions();
    }

    private void showInstructions() {
        String instructions = "1. Arrastra las piezas del rompecabezas a las zonas correspondientes.\n" +
                "2. Asegúrate de que las ecuaciones estén completas correctamente.\n" +
                "3. Toca el botón de verificar para comprobar tus respuestas.\n" +
                "4. Si necesitas reiniciar el juego, presiona el botón de reiniciar.";

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MiAlertDialog);
        builder.setTitle("Instrucciones del Juego")
                .setMessage(instructions)
                .setPositiveButton("¡Entendido!", (dialog, which) -> dialog.dismiss())
                .setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(d -> {
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setTextColor(getResources().getColor(R.color.white));
            positiveButton.setBackgroundColor(getResources().getColor(R.color.green));
        });
        dialog.show();
    }

    private void initializeViews() {
        dropZones = new TextView[]{
                findViewById(R.id.dropZone1_1),
                findViewById(R.id.dropZone1_2),
                findViewById(R.id.dropZone2_1),
                findViewById(R.id.dropZone2_2)
        };

        puzzlePieces = new TextView[]{
                findViewById(R.id.piece1),
                findViewById(R.id.piece2),
                findViewById(R.id.piece3),
                findViewById(R.id.piece4)
        };

        originalX = new float[puzzlePieces.length];
        originalY = new float[puzzlePieces.length];
    }

    private void saveOriginalPositions() {
        for (int i = 0; i < puzzlePieces.length; i++) {
            final int index = i;
            puzzlePieces[i].post(() -> {
                originalX[index] = puzzlePieces[index].getX();
                originalY[index] = puzzlePieces[index].getY();
            });
        }
    }

    private void setupDragAndDrop() {
        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        selectedPiece = (TextView) view;
                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();
                        activePointerId = event.getPointerId(0);
                        view.setElevation(8f);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        if (selectedPiece != null) {
                            selectedPiece.setX(event.getRawX() + dX);
                            selectedPiece.setY(event.getRawY() + dY);
                        }
                        return true;

                    case MotionEvent.ACTION_UP:
                        if (selectedPiece != null) {
                            checkDropZone(selectedPiece, event.getRawX(), event.getRawY());
                            selectedPiece.setElevation(1f);
                            selectedPiece = null;
                        }
                        return true;

                    default:
                        return false;
                }
            }
        };

        for (TextView piece : puzzlePieces) {
            piece.setOnTouchListener(touchListener);
        }
    }

    private void checkDropZone(TextView piece, float x, float y) {
        boolean dropped = false;

        for (TextView dropZone : dropZones) {
            if (isViewOverlapping(dropZone, x, y)) {
                if (dropZone.getText().toString().isEmpty()) {
                    dropZone.setText(piece.getText());
                    piece.setVisibility(View.INVISIBLE);
                    dropped = true;
                    break;
                }
            }
        }

        if (!dropped) {
            piece.setX(originalX[indexOf(puzzlePieces, piece)]);
            piece.setY(originalY[indexOf(puzzlePieces, piece)]);
        }
    }

    private int indexOf(TextView[] array, TextView item) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == item) return i;
        }
        return -1;
    }

    private boolean isViewOverlapping(View dropZone, float x, float y) {
        int[] location = new int[2];
        dropZone.getLocationOnScreen(location);
        int dropZoneX = location[0];
        int dropZoneY = location[1];

        return (x >= dropZoneX && x <= dropZoneX + dropZone.getWidth() &&
                y >= dropZoneY && y <= dropZoneY + dropZone.getHeight());
    }

    private void setupCheckButton() {
        Button checkButton = findViewById(R.id.checkButton);
        checkButton.setOnClickListener(v -> checkAnswers());
    }

    private void setupResetButton() {
        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(v -> resetPuzzle());
    }

    private void resetPuzzle() {
        for (TextView dropZone : dropZones) {
            dropZone.setText("");
        }

        for (int i = 0; i < puzzlePieces.length; i++) {
            TextView piece = puzzlePieces[i];
            piece.setVisibility(View.VISIBLE);
            piece.setX(originalX[i]);
            piece.setY(originalY[i]);
            piece.setElevation(1f);
        }

        selectedPiece = null;
    }

    private void checkAnswers() {
        // Obtener los textos actuales de las zonas de destino
        String[] answers = new String[dropZones.length];
        for (int i = 0; i < dropZones.length; i++) {
            answers[i] = dropZones[i].getText().toString().trim();
        }

        // Verificar las respuestas para factor común
        boolean firstEquationCorrect = answers[0].equals(String.format("%d", a1)) &&
                answers[1].equals(String.format("(%dx^%d + %d)", b1, x1, b2));

        // Segunda ecuación
        boolean secondEquationCorrect = answers[2].equals(String.format("%d", a2)) &&
                answers[3].equals(String.format("(%dx^%d + %d)", b1, x1, b2));

        boolean isCorrect = firstEquationCorrect && secondEquationCorrect;

        // Mostrar resultado
        String message = isCorrect ? "¡Correcto! ¡Muy bien!" : "Intenta de nuevo";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void generateRandomPuzzle() {
        // Generar dos expresiones con factor común aleatorio
        a1 = random.nextInt(10) + 1;
        b1 = random.nextInt(5) + 1;
        x1 = random.nextInt(3) + 1;
        a2 = random.nextInt(10) + 1;
        b2 = random.nextInt(5) + 1;
        x2 = random.nextInt(3) + 1;

        String expression1 = String.format("%dx^%d + %dx^%d", a1 * b1, x1 + 1, a1*b2, x1);
        String factor1 = String.format("%d", a1);
        String term1_1 = String.format("(%dx^%d + %d)", b1, x1, b2);

        String expression2 = String.format("%dx^%d + %dx^%d", a2 * b1, x2 + 1, a2*b2, x2);
        String factor2 = String.format("%d", a2);
        String term2_1 = String.format("(%dx^%d + %d)", b1, x1, b2);


        // Asignar las expresiones y factores a los TextViews
        TextView equation1 = findViewById(R.id.equation1);
        TextView equation2 = findViewById(R.id.equation2);
        equation1.setText(expression1);
        equation2.setText(expression2);

        TextView piece1 = findViewById(R.id.piece1);
        TextView piece2 = findViewById(R.id.piece2);
        TextView piece3 = findViewById(R.id.piece3);
        TextView piece4 = findViewById(R.id.piece4);
        piece1.setText(factor1);
        piece2.setText(term1_1);
        piece3.setText(factor2);
        piece4.setText(term2_1);

        // Reiniciar el puzzle para que las piezas estén en su posición original
        resetPuzzle();
    }

    @Override
    protected void onStop(){
        super.onStop();
        SoundManager.getInstance().stopQuizSound();
    }
}