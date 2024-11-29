package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

public class Card {
    private int id;
    private String question;
    private String answer;
    private boolean isFlipped;
    private boolean isMatched;

    public Card(int id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.isFlipped = false;
        this.isMatched = false;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }
    public boolean isFlipped() { return isFlipped; }
    public void setFlipped(boolean flipped) { isFlipped = flipped; }
    public boolean isMatched() { return isMatched; }
    public void setMatched(boolean matched) { isMatched = matched; }
}