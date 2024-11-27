package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Settings extends Fragment {

    private Button log_out, exit;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        mAuth = FirebaseAuth.getInstance();

        log_out = view.findViewById(R.id.logout_btn);
        exit = view.findViewById(R.id.exit_btn);

        log_out.setOnClickListener(v -> logout());

        exit.setOnClickListener(v -> exitApp()) ;

        return view;
    }

    private void logout(){
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null){
            mAuth.signOut();
            Intent intent = new Intent(requireContext(), Login.class);
            startActivity(intent);
            requireActivity().finish();
        }
    }

    private void exitApp(){
        requireActivity().finishAffinity(); // Cierra todas las actividades en la pila
        System.exit(0); // Finaliza el proceso actual de la app
    }
}