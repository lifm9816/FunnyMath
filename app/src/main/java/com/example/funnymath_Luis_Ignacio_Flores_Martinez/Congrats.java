package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Congrats extends AppCompatActivity {

    private Button return_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congrats);

        SoundManager.getInstance().playQuizSound(this, R.raw.celebrating);

        return_btn = findViewById(R.id.return_btn);
        return_btn.setOnClickListener(v -> goSection());
    }

    public void goSection(){
        Intent intent = new Intent(this, BottomMenu.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop(){
        super.onStop();
        SoundManager.getInstance().stopQuizSound();
    }
}