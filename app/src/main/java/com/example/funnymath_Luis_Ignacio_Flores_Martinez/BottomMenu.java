package com.example.funnymath_Luis_Ignacio_Flores_Martinez;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class BottomMenu extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_menu);

        SoundManager.getInstance().playMainSound(this, R.raw.gamemenu);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        frameLayout = findViewById(R.id.main_container);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    loadFragment(new Learning(), true);
                } else if (itemId == R.id.nav_learning) {
                    loadFragment(new Home(), true);
                } else if (itemId == R.id.nav_account) {
                    loadFragment(new Account(), true);
                } else if (itemId == R.id.nav_power) {
                    loadFragment(new Settings(), true);
                } else if (itemId == R.id.nav_info) {
                    loadFragment(new About(), true);
                }
                return true;
            }
        });

        loadFragment(new Learning(), true);
    }

    private void loadFragment(Fragment fragment, boolean isAppInitialized){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(isAppInitialized){
            fragmentTransaction.replace(R.id.main_container, fragment);
        }else{
            fragmentTransaction.add(R.id.main_container, fragment);
        }
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(!SoundManager.getInstance().isMainSoundPlaying()){
            SoundManager.getInstance().resumeMainSound();
        }
    }
}