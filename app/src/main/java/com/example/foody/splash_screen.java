package com.example.foody;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.content.SharedPreferences;


public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Cek status login
                if (isLoggedIn()) {
                    startActivity(new Intent(splash_screen.this, HomeFoodyActivity.class));
                } else {
                    startActivity(new Intent(splash_screen.this, hal_awal.class));
                }
                finish();
            }
        }, 3000);
    }

    private boolean isLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_status", MODE_PRIVATE);
        return sharedPreferences.getBoolean("is_logged_in", false);
    }
}
