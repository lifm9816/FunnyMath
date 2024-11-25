package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Signin extends AppCompatActivity {

    private EditText name, lastname, email, phone, password, conf_pass; //Campos de texto para los datos del usuario
    private Button create_btn; //Botón para crear la cuenta
    private FirebaseAuth mAuth; //Instancia de Firebase Authentication
    private FirebaseFirestore db; //Instancia de Firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance(); //Obtiene la instancia de Firebase Authentication
        db = FirebaseFirestore.getInstance(); //Obtiene la instancia de Firestore

        //Obtiene las referencias a los elementos del layout
        name = findViewById(R.id.name);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        conf_pass = findViewById(R.id.conf_pass);
        create_btn = findViewById(R.id.create_btn);

        //Listener para el botón de crear cuenta
        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            } //Llama al metodo registerUser
        });

        //Configura un listener para ajustar el padding de la vista principal según los system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //Metodo para registrar al usuario
    private void registerUser(){
        //Obtiene los valores ingresados por el usuario
        String nameR = name.getText().toString().trim();
        String lastnameR = lastname.getText().toString().trim();
        String emailR = email.getText().toString().trim();
        String phoneR = phone.getText().toString().trim();
        String passwordR = password.getText().toString().trim();
        String conf_passR = conf_pass.getText().toString().trim();

        //Valida que los campos no estén vaciós
        if(nameR.isEmpty() || lastnameR.isEmpty() || emailR.isEmpty() || phoneR.isEmpty() || passwordR.isEmpty() || conf_passR.isEmpty()) {
            Toast.makeText(Signin.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        } else if(!passwordR.equals(conf_passR)) {
            Toast.makeText(Signin.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        } else {
            //Crea el usuario con Firebase Authentication
            mAuth.createUserWithEmailAndPassword(emailR, passwordR)
                    .addOnCompleteListener(Signin.this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String userId = user.getUid();
                                UserProfile userProfile = new UserProfile(userId, nameR, lastnameR, emailR, phoneR);

                                db.collection("users").document(userId)
                                        .set(userProfile)
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(Signin.this, "Usuario creado correctamente", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(Signin.this, BottomMenu.class);
                                            startActivity(intent);
                                            finish();
                                        })
                                        .addOnFailureListener(e -> {
                                            String errorMessage = e.getMessage();
                                            Toast.makeText(Signin.this, "Error al crear perfil: " + errorMessage, Toast.LENGTH_LONG).show();
                                            // Log de depuración
                                            Log.e("FirestoreError", errorMessage);
                                        });
                            } else {
                                Log.e("FirebaseAuth", "Error: User object is null after successful registration.");
                            }
                        } else {
                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Error desconocido";
                            Toast.makeText(Signin.this, "Error en el registro: " + errorMessage, Toast.LENGTH_LONG).show();
                            // Log de depuración
                            Log.e("FirebaseAuth", errorMessage);
                        }
                    });

        }
    }
}