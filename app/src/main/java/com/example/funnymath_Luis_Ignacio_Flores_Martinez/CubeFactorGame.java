package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import java.util.Random;

public class CubeFactorGame {
    private Random random;
    private int a;
    private int b;
    private boolean isSum;
    private int score;
    private static final int MAX_NUMBER = 10;
    private static final int POINTS_PER_CORRECT = 10;

    // Constructor corregido - quitado el "void"
    public CubeFactorGame() {
        this.random = new Random();
        this.score = 0;
        this.a = 0;
        this.b = 0;
        this.isSum = true;
    }

    public String generateProblem() {
        if (random == null) {
            random = new Random();
        }
        a = random.nextInt(MAX_NUMBER) + 1;
        b = random.nextInt(MAX_NUMBER) + 1;
        isSum = random.nextBoolean();

        String operator = isSum ? " + " : " - ";
        return a + "³" + operator + b + "³";
    }

    // El resto del código se mantiene igual
    public boolean checkAnswer(String firstTerm, String secondTerm) {
        try {
            int firstTermValue = Integer.parseInt(firstTerm.trim());
            int secondTermValue = Integer.parseInt(secondTerm.trim());

            int expectedFirstTerm = isSum ? (a + b) : (a - b);
            int expectedSecondTerm = calculateSecondTerm();

            boolean isCorrect = firstTermValue == expectedFirstTerm &&
                    secondTermValue == expectedSecondTerm;

            if (isCorrect) {
                score += POINTS_PER_CORRECT;
            }

            return isCorrect;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private int calculateSecondTerm() {
        int aSquared = a * a;
        int bSquared = b * b;
        int abTerm = a * b;
        return aSquared + (isSum ? -1 : 1) * abTerm + bSquared;
    }

    public String getHint() {
        String formula;
        if (isSum) {
            formula = "a³ + b³ = (a + b)(a² - ab + b²)";
        } else {
            formula = "a³ - b³ = (a - b)(a² + ab + b²)";
        }
        return "Fórmula para factorizar:\n" + formula;
    }

    public String getCorrectAnswer() {
        int firstTerm = isSum ? (a + b) : (a - b);
        int secondTerm = calculateSecondTerm();
        return String.format("(%d)(%d)", firstTerm, secondTerm);
    }

    public int getScore() {
        return score;
    }

    public boolean getIsSum() {
        return isSum;
    }

    public void resetScore() {
        score = 0;
    }

    public int getCurrentA() {
        return a;
    }

    public int getCurrentB() {
        return b;
    }
}