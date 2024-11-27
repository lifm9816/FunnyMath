package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Account extends Fragment {

    private ImageButton photo;
    private EditText name, lastname, email, phone;
    private Button save_changes, delete_account;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        photo = view.findViewById(R.id.photo);
        name = view.findViewById(R.id.name);
        lastname = view.findViewById(R.id.lastname);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        save_changes = view.findViewById(R.id.save_btn);
        delete_account = view.findViewById(R.id.delete_btn);

        loadUserData();
        loadImageFromInternalStorage();

        save_changes.setOnClickListener(v -> saveChanges());
        delete_account.setOnClickListener(v -> showDeleteConfirmationDialog());

        return view;
    }

    private void loadUserData() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String userId = user.getUid();
            DocumentReference docRef = db.collection("users").document(userId);
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        UserProfile userProfile = document.toObject(UserProfile.class);

                        if (userProfile != null) {
                            name.setText(userProfile.getName());
                            lastname.setText(userProfile.getLastname());
                            email.setText(userProfile.getEmail());
                            phone.setText(userProfile.getPhone());
                        }
                    } else {
                        Log.d("Firestore", "No existe el documento");
                    }
                } else {
                    Log.d("Firestore", "Error al obtener el documento", task.getException());
                }
            });
        }
    }

    private void loadImageFromInternalStorage() {
        try {
            File directory = requireContext().getDir("imageDir", requireContext().MODE_PRIVATE);
            File mypath = new File(directory, "profile.jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(mypath));
            photo.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            Log.e("Account", "Error al cargar la imagen", e);
        } catch (NullPointerException e) {
            Log.e("Account", "Error de contexto nulo", e);
            Toast.makeText(requireContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveChanges() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            String newName = name.getText().toString().trim();
            String newLastname = lastname.getText().toString().trim();
            String newEmail = email.getText().toString().trim();
            String newPhone = phone.getText().toString().trim();

            UserProfile updatedProfile = new UserProfile(userId, newName, newLastname, newEmail, newPhone);

            db.collection("users").document(userId)
                    .set(updatedProfile)
                    .addOnSuccessListener(aVoid -> Toast.makeText(requireContext(), "Datos actualizados con éxito!", Toast.LENGTH_LONG).show())
                    .addOnFailureListener(e -> {
                        Toast.makeText(requireContext(), "Error al actualizar los datos", Toast.LENGTH_LONG).show();
                        Log.e("FirestoreError", e.getMessage());
                    });
        }
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Eliminar cuenta")
                .setMessage("¿Estás seguro de que quieres eliminar tu cuenta? Esta acción no se puede deshacer.")
                .setPositiveButton("Eliminar", (dialog, which) -> showPasswordDialog())
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void showPasswordDialog() {
        final EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        new AlertDialog.Builder(requireContext())
                .setTitle("Ingrese su contraseña")
                .setMessage("Para confirmar, por favor ingresa tu contraseña.")
                .setView(input)
                .setPositiveButton("Aceptar", (dialogInterface, which) -> {
                    String password = input.getText().toString();
                    if (!password.isEmpty()) {
                        reauthenticateAndDeleteUser(password);
                    } else {
                        Toast.makeText(requireContext(), "Por favor, ingresa tu contraseña.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void reauthenticateAndDeleteUser(String password) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password);
            user.reauthenticate(credential)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            deleteUser();
                        } else {
                            Log.e("AuthError", "Error de re-autenticación", task.getException());
                            Toast.makeText(requireContext(), "Error al autenticar la cuenta. Verifica tu contraseña.", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private void deleteUser() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            db.collection("users").document(userId)
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        user.delete()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        mAuth.signOut();
                                        Intent intent = new Intent(requireContext(), Login.class);
                                        startActivity(intent);
                                        requireActivity().finish();
                                    } else {
                                        Log.e("Authentication", "Error al eliminar la cuenta de Authentication", task.getException());
                                        Toast.makeText(requireContext(), "Error al eliminar la cuenta de Authentication", Toast.LENGTH_LONG).show();
                                    }
                                });
                    })
                    .addOnFailureListener(e -> {
                        Log.e("Firestore", "Error al eliminar el documento de Firestore", e);
                        Toast.makeText(requireContext(), "Error al eliminar la cuenta", Toast.LENGTH_LONG).show();
                    });
        }
    }
}
