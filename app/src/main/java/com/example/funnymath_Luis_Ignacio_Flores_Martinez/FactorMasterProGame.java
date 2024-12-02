package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FactorMasterProGame {
    private int currentLevel;
    private int score;
    private int hintsUsed;
    private QuadraticExpression currentExpression;
    private static final int MAX_LEVELS = 10;

    public FactorMasterProGame() {
        this.currentLevel = 1;
        this.score = 0;
        this.hintsUsed = 0;
        generateNewExpression();
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
        generateNewExpression();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public int getHintsUsed() {
        return hintsUsed;
    }

    public void setHintsUsed(int hintsUsed) {
        this.hintsUsed = hintsUsed;
    }

    public void useHint() {
        hintsUsed++;
    }

    public boolean isGameFinished() {
        return currentLevel > MAX_LEVELS;
    }

    public void nextLevel() {
        currentLevel++;
        hintsUsed = 0;
        generateNewExpression();
    }

    public QuadraticExpression getCurrentExpression() {
        return currentExpression;
    }

    private void generateNewExpression() {
        // Genera expresiones más difíciles según el nivel
        int a = 1;
        int b = (currentLevel <= 5) ? (int)(Math.random() * 10) - 5 : (int)(Math.random() * 20) - 10;
        int c = (currentLevel <= 5) ? (int)(Math.random() * 10) - 5 : (int)(Math.random() * 20) - 10;
        currentExpression = new QuadraticExpression(a, b, c);
    }

    public static class QuadraticExpression {
        private int a, b, c;
        private int factor1, factor2;

        public QuadraticExpression(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
            calculateFactors();
        }

        private void calculateFactors() {
            // Encuentra los factores que dan b como suma y c como producto
            for (int i = -10; i <= 10; i++) {
                for (int j = -10; j <= 10; j++) {
                    if (i + j == b && i * j == c) {
                        factor1 = i;
                        factor2 = j;
                        break;
                    }
                }
            }
        }

        public String getExpression() {
            return "x² " + (b >= 0 ? "+ " : "- ") + Math.abs(b) + "x " +
                    (c >= 0 ? "+ " : "- ") + Math.abs(c);
        }

        public String getFactorsHint() {
            return "Los factores son números entre -10 y 10 que suman " + b + " y multiplican " + c;
        }

        public String getSumsHint() {
            return "Busca dos números que sumen " + b + " y su producto sea " + c;
        }

        public String getFactoredHint() {
            return "La forma factorizada será (x + p)(x + q) donde p y q son números enteros";
        }

        public boolean checkFactors(String input) {
            String[] numbers = input.split(",");
            if (numbers.length != 2) return false;
            try {
                int f1 = Integer.parseInt(numbers[0].trim());
                int f2 = Integer.parseInt(numbers[1].trim());
                return (f1 == factor1 && f2 == factor2) || (f1 == factor2 && f2 == factor1);
            } catch (NumberFormatException e) {
                return false;
            }
        }

        public boolean checkSums(int sum1, int sum2) {
            return (sum1 == factor1 && sum2 == factor2) || (sum1 == factor2 && sum2 == factor1);
        }

        public boolean checkFactored(String input) {
            // Formato esperado: (x+p)(x+q) donde p y q son los factores
            input = input.replaceAll("\\s+", "");
            String pattern = "\\(x[+-]" + Math.abs(factor1) + "\\)\\(x[+-]" + Math.abs(factor2) + "\\)";
            String pattern2 = "\\(x[+-]" + Math.abs(factor2) + "\\)\\(x[+-]" + Math.abs(factor1) + "\\)";
            return input.matches(pattern) || input.matches(pattern2);
        }
    }
}