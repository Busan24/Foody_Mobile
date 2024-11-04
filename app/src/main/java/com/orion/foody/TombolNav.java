package com.orion.foody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TombolNav extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tombol_nav);

        // Inisialisasi BottomNavigationView
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

        // Tambahkan pendengar untuk menangani navigasi
        bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    // Buka halaman HomeFoodyActivity
                    Intent intent = new Intent(TombolNav.this, HomeFoodyActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_catatanku) {
                    // Buka halaman activity_fitur_catatanku
                    Intent intent = new Intent(TombolNav.this, fitur_catatanku.class);
                    startActivity(intent);
                } else if (id == R.id.nav_profil) {
                    // Buka halaman FiturProfil
                    Intent intent = new Intent(TombolNav.this, FiturProfil.class);
                    startActivity(intent);
                }
                return true;
            }
        });

    }

    // ...
}
