package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private List<Card> cards;
    private OnCardClickListener listener;

    public CardAdapter(List<Card> cards, OnCardClickListener listener) {
        this.cards = cards;
        this.listener = listener;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Card card = cards.get(position);
        if (card.isFlipped() || card.isMatched()) {
            holder.cardText.setText(card.getQuestion());
        } else {
            holder.cardText.setText("?");
        }

        holder.cardView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCardClick(position);
            }
        });

        if (card.isMatched()) {
            holder.cardView.setBackgroundColor(Color.GRAY);
        } else if (card.isFlipped()) {
            holder.cardView.setBackgroundColor(Color.BLUE);
        } else {
            holder.cardView.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public interface OnCardClickListener {
        void onCardClick(int position);
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView cardText;
        CardView cardView;

        CardViewHolder(View itemView) {
            super(itemView);
            cardText = itemView.findViewById(R.id.cardText);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}