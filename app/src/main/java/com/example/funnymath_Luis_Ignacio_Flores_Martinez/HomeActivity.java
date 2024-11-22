package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.funnymath_Luis_Ignacio_Flores_Martinez.databinding.ActivityHomeBinding;

public class HomeActivity extends BottomMenu {

    ActivityHomeBinding activityHomeBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);


        activityHomeBinding = activityHomeBinding.inflate(getLayoutInflater());
        setContentView(activityHomeBinding.getRoot());
    }
}