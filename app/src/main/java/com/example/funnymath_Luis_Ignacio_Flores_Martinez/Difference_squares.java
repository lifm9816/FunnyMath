package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Difference_squares extends Floating_button {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difference_squares);

        configureMenu2(R.drawable.evaluating, Difference_squares.class);
    }
}