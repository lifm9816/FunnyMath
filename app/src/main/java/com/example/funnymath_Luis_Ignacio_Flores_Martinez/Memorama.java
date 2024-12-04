package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.funnymath_Luis_Ignacio_Flores_Martinez.CardAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Memorama extends Floating_button implements CardAdapter.OnCardClickListener {
    private RecyclerView recyclerView;
    private CardAdapter adapter;
    private List<Card> cards;
    private int firstSelectedPosition = -1;
    private int moves = 0;
    private TextView movesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorama);

        SoundManager.getInstance().pauseMainSound();
        SoundManager.getInstance().playQuizSound(this, R.raw.game2);

        home_btn = findViewById(R.id.home_btn);
        menu2_btn = findViewById(R.id.menu2_btn);

        home_btn.setOnClickListener(v -> goToActivity(BottomMenu.class));

        menu2_btn.setIcon(R.drawable.evaluating);
        menu2_btn.setOnClickListener(v -> goToActivity(Difference_squares.class));

        recyclerView = findViewById(R.id.recyclerView);
        movesText = findViewById(R.id.movesText);

        initializeCards();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new CardAdapter(cards, this);
        recyclerView.setAdapter(adapter);

        showInstructionsDialog();
    }

    private void showInstructionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MiAlertDialog);
        builder.setTitle("Instrucciones")
                .setMessage("Bienvenido al Memorama de FactoFunny. Encuentra las parejas de expresiones y sus factorizaciones correspondientes. ¡Recuerda cada movimiento cuenta, así que juega estratégicamente!")
                .setPositiveButton("Entendido", (dialog, which) -> dialog.dismiss())
                .setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(d -> {
            Button positiveButton = dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE);
            positiveButton.setTextColor(getResources().getColor(R.color.white));
            positiveButton.setBackgroundColor(getResources().getColor(R.color.green));
        });
        dialog.show();
    }


    private void initializeCards() {
        cards = new ArrayList<>();
        // Crear las parejas de cartas
        cards.add(new Card(1, "x² - 16", "(x+4)(x-4)"));
        cards.add(new Card(2, "25y² - 4", "(5y+2)(5y-2)"));
        cards.add(new Card(3, "a² - 81", "(a+9)(a-9)"));
        cards.add(new Card(4, "49 - m²", "(7+m)(7-m)"));
        cards.add(new Card(5, "x² - 1", "(x+1)(x-1)"));
        cards.add(new Card(6, "100 - b²", "(10+b)(10-b)"));
        cards.add(new Card(7, "x² - 36", "(x+6)(x-6)"));
        cards.add(new Card(8, "4y² - 25", "(2y+5)(2y-5)"));
        cards.add(new Card(9, "121 - z²", "(11+z)(11-z)"));
        cards.add(new Card(10, "x² - 144", "(x+12)(x-12)"));

        // Duplicar las cartas y mezclarlas
        List<Card> allCards = new ArrayList<>();
        for (Card card : cards) {
            allCards.add(card);
            allCards.add(new Card(card.getId(), card.getAnswer(), card.getQuestion()));
        }
        Collections.shuffle(allCards);
        cards = allCards;
    }

    @Override
    public void onCardClick(int position) {
        Card selectedCard = cards.get(position);

        // Si la carta ya está volteada o emparejada, no hacer nada
        if (selectedCard.isFlipped() || selectedCard.isMatched()) return;

        // Voltear la carta seleccionada
        selectedCard.setFlipped(true);
        adapter.notifyDataSetChanged();

        if (firstSelectedPosition == -1) {
            firstSelectedPosition = position;
        } else {
            moves++;
            movesText.setText("Movimientos: " + moves);

            Card firstCard = cards.get(firstSelectedPosition);

            if (firstCard.getId() == selectedCard.getId()) {
                firstCard.setMatched(true);
                selectedCard.setMatched(true);
                checkWinCondition();
            } else {
                // Esperar 1 segundo antes de voltear las cartas
                new Handler().postDelayed(() -> {
                    firstCard.setFlipped(false);
                    selectedCard.setFlipped(false);
                    adapter.notifyDataSetChanged();
                }, 1000);
            }
            firstSelectedPosition = -1;
        }
    }

    private void checkWinCondition() {
        boolean allMatched = true;
        for (Card card : cards) {
            if (!card.isMatched()) {
                allMatched = false;
                break;
            }
        }

        if (allMatched) {
            showWinnerDialog();
        }
    }

    private void showWinnerDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.winner_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        Button btnQuiz = dialogView.findViewById(R.id.btnQuiz);
        Button btnRestart = dialogView.findViewById(R.id.btnRestart);

        btnQuiz.setOnClickListener(v -> {
            // Agregar aquí el Intent para ir al Quiz
            startActivity(new Intent(Memorama.this, Difference_squares_quizz.class));
            dialog.dismiss();
        });

        btnRestart.setOnClickListener(v -> {
            startActivity(new Intent(Memorama.this, Difference_squares_theory.class));
            dialog.dismiss();
        });

        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected void onStop(){
        super.onStop();
        SoundManager.getInstance().stopQuizSound();
    }
}