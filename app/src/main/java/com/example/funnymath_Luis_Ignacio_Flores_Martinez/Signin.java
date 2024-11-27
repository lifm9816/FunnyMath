package com.example.funnymath_Luis_Ignacio_Flores_Martinez;



import android.content.Context;

import android.content.ContextWrapper;

import android.content.Intent;

import android.graphics.Bitmap;

import android.graphics.BitmapFactory;

import android.net.Uri;

import android.os.Bundle;

import android.provider.MediaStore;

import android.util.Log;

import android.view.View;

import android.widget.Button;

import android.widget.EditText;

import android.widget.ImageButton;

import android.widget.Toast;



import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.graphics.Insets;

import androidx.core.view.ViewCompat;

import androidx.core.view.WindowInsetsCompat;



import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.FirebaseFirestore;



import java.io.File;

import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;

import java.io.IOException;



public class Signin extends AppCompatActivity {



    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText name, lastname, email, phone, password, conf_pass;

    private Button create_btn;

    private FirebaseAuth mAuth;

    private FirebaseFirestore db;

    private ImageButton photo; // Declaración del ImageButton



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        name = findViewById(R.id.name);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        conf_pass = findViewById(R.id.conf_pass);
        create_btn = findViewById(R.id.create_btn);
        photo = findViewById(R.id.photo); // Obtener la referencia al ImageButton

        create_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        photo.setOnClickListener(new View.OnClickListener() { // Agregar OnClickListener al ImageButton

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        loadImageFromInternalStorage(); // Cargar la imagen al iniciar la actividad

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

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

    // Metodo para guardar la imagen en el almacenamiento interno
    private String saveImageToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    // Metodo para cargar la imagen desde el almacenamiento interno
    private void loadImageFromInternalStorage() {

        ContextWrapper cw = new ContextWrapper(getApplicationContext());

        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        File mypath=new File(directory,"profile.jpg");

        try {

            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(mypath));

            photo.setImageBitmap(bitmap);

        } catch (FileNotFoundException e) {

            // No se encontró la imagen, usar imagen por defecto o no hacer nada

        }

    }



    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri imageUri = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                saveImageToInternalStorage(bitmap); // Guardar la imagen

                photo.setImageBitmap(bitmap); // Mostrar la imagen en el ImageButton

            } catch (IOException e) {

                e.printStackTrace();

                Toast.makeText(Signin.this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();

            }

        }

    }

}