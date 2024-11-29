package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
        if (cards.get(position).isMatched()) return;

        if (firstSelectedPosition == -1) {
            // Primera carta seleccionada
            firstSelectedPosition = position;
            cards.get(position).setFlipped(true);
        } else {
            // Segunda carta seleccionada
            moves++;
            movesText.setText("Movimientos: " + moves);

            if (cards.get(firstSelectedPosition).getId() == cards.get(position).getId()) {
                // ¡Encontró un par!
                cards.get(firstSelectedPosition).setMatched(true);
                cards.get(position).setMatched(true);
            } else {
                // No coinciden, voltear de nuevo
                final int firstPos = firstSelectedPosition;
                new Handler().postDelayed(() -> {
                    cards.get(firstPos).setFlipped(false);
                    cards.get(position).setFlipped(false);
                    adapter.notifyDataSetChanged();
                }, 1000);
            }
            firstSelectedPosition = -1;
        }
        adapter.notifyDataSetChanged();
    }
}