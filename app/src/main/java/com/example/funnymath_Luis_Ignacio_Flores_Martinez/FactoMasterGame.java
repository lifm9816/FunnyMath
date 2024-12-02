package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import java.util.ArrayList;
import java.util.List;

public class FactoMasterGame {
    private static final int TOTAL_EXERCISES = 7;
    private int currentExercise = 0;
    private int score = 0;
    private List<Exercise> exercises;

    public static class Exercise {
        private final String trinomial;
        private final String correctAnswer;

        public Exercise(String trinomial, String correctAnswer) {
            this.trinomial = trinomial;
            this.correctAnswer = correctAnswer;
        }

        public String getTrinomial() {
            return trinomial;
        }

        public boolean checkAnswer(String answer) {
            return answer.replaceAll("\\s+", "")
                    .equals(correctAnswer.replaceAll("\\s+", ""));
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }
    }

    public FactoMasterGame() {
        initializeExercises();
    }

    private void initializeExercises() {
        exercises = new ArrayList<>();
        // 7 pre-set exercises with progressive difficulty
        exercises.add(new Exercise("x² + 6x + 9", "(x + 3)²"));
        exercises.add(new Exercise("x² - 8x + 16", "(x - 4)²"));
        exercises.add(new Exercise("4x² + 12x + 9", "(2x + 3)²"));
        exercises.add(new Exercise("9x² - 24x + 16", "(3x - 4)²"));
        exercises.add(new Exercise("25x² + 70x + 49", "(5x + 7)²"));
        exercises.add(new Exercise("16x² - 40x + 25", "(4x - 5)²"));
        exercises.add(new Exercise("36x² + 84x + 49", "(6x + 7)²"));
    }

    public boolean checkAnswer(String userAnswer) {
        if (currentExercise >= TOTAL_EXERCISES) {
            return false;
        }

        boolean isCorrect = exercises.get(currentExercise)
                .checkAnswer(userAnswer);
        if (isCorrect) {
            score += 10;
        }
        return isCorrect;
    }

    public void nextExercise() {
        if (currentExercise < TOTAL_EXERCISES - 1) {
            currentExercise++;
        }
    }

    public boolean isGameFinished() {
        return currentExercise >= TOTAL_EXERCISES - 1;
    }

    public String getCurrentExercise() {
        return exercises.get(currentExercise).getTrinomial();
    }

    public int getCurrentExerciseNumber() {
        return currentExercise + 1;
    }

    public int getScore() {
        return score;
    }

    public String getCorrectAnswer() {
        return exercises.get(currentExercise).getCorrectAnswer();
    }
}