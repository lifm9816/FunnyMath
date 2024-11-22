package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText emailLogin, passwordLogin; //Campos de texto para email y login
    private TextView register_btn; //Textview para ir al registro
    private Button login; //Botón para iniciar sesión
    private FirebaseAuth mAuth; //Instancia de Firebase Authentication

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); //Habilita el modo "Edge to Edge" para la actividad
        setContentView(R.layout.activity_login);

        //Obtiene las referencias a los elementos del layout
        emailLogin = findViewById(R.id.email);
        passwordLogin = findViewById(R.id.password);
        login = findViewById(R.id.login_btn);
        register_btn = findViewById(R.id.register_btn);

        mAuth = FirebaseAuth.getInstance(); //Obtiene la instancia de Firebase Authentication

        //Listener para el botón del login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Obtiene el email y la contraseña ingresados por el usuario
                String email = emailLogin.getText().toString().trim();
                String password = passwordLogin.getText().toString().trim();

                //Valida que los campos no estén vacíos
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(Login.this,"El correo o la contraseña no pueden estar vacíos", Toast.LENGTH_LONG).show();
                    return; //Sale del metodo si hay campos vacíos
                }
                else{
                    //Inicia sessión con Firebase Authentication
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Login.this, task -> {
                                if(task.isSuccessful()){ //Si el inicio de sesión es exitoso
                                    FirebaseUser user = mAuth.getCurrentUser(); //Obtiene el usuario actual
                                    Toast.makeText(Login.this, "Bienvenido " + user.getEmail(), Toast.LENGTH_LONG).show(); //Muestra un mensaje de bienvenida
                                    Intent intent = new Intent(Login.this, BottomMenu.class); //Crea un intent para ir al BottomMenu
                                    startActivity(intent); //Inicia la actividad BottomMenu
                                    finish(); //Cierra la actividad Login
                                }else{ //Si el inicio de sesión falla
                                    Toast.makeText(Login.this, "El correo o la contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        //Listener para el TextView de registro
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Signin.class); //Crea un Intent para ir a la actividad de registro
                startActivity(intent); //Inicia la actividad Signin
            }
        });

        //Configura un listener para ajustar el padding de la vista principal según los system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}