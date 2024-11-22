package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar; //Referencia a la barra de progreso en el layout
    private int progressStatus = 0; //Valor actual del progreso (0 - 100)

    private Handler handler = new Handler(); //Handler para actualizar la UI desde el Thread

    private FirebaseAuth mAuth; //Instancia de Firebase Authentication

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar); //Obtiene la referencia a la barra de progreso

        mAuth = FirebaseAuth.getInstance(); //Obtiena la instancia de Firebase Authentication
        FirebaseUser currentUser = mAuth.getCurrentUser(); //Obtiene al usuario actual

        // Simula el progreso en la barra de carga
        new Thread(new Runnable() { // Crea nuevo Thread para simular la carga
            @Override
            public void run() { //Metodo que se ejecuta en el Thread
                while (progressStatus < 100) { //Ciclo para simular el progreso
                    progressStatus++; //Incrementa el progreso
                    SystemClock.sleep(30); // Pausa el Thread por 30 milisegundos
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus); //Actualiza la barra de progreso
                        }
                    });
                }

                //Verifica si el usuario ha iniciado sesión después de simular la carga
                if (currentUser != null) {
                    //El usuario ya ha iniciado sesión y redirige a la pantalla principal
                    Intent intent = new Intent(MainActivity.this, BottomMenu.class);
                    startActivity(intent);

                } else {
                    //El usuario no ha iniciado sesción y se redirige al Login
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                }
                finish(); //Cierra la pantalla de carga después de iniciar la nueva actividad
            }
        }).start(); //Inicia le Thread
    }
}
