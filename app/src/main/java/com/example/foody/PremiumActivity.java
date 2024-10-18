package com.example.foody;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PremiumActivity extends AppCompatActivity {

    private Button prem1Bulan, prem3Bulan, prem6Bulan, berlangganan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);

        // Inisialisasi button
        prem1Bulan = findViewById(R.id.prem_1bulan);
        prem3Bulan = findViewById(R.id.prem_3bulan);
        prem6Bulan = findViewById(R.id.prem_6bulan);
        berlangganan = findViewById(R.id.berlangganan);

        // Set tombol 1 bulan aktif di awal
        setActiveButton(prem1Bulan, "Berlangganan 1 Bulan", R.drawable.kotak_prem_pink);

        // Klik tombol 1 bulan
        prem1Bulan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveButton(prem1Bulan, "Berlangganan 1 Bulan", R.drawable.kotak_prem_pink);
                setInactiveButton(prem3Bulan, R.drawable.kotak_prem_abu);
                setInactiveButton(prem6Bulan, R.drawable.kotak_prem_abu);
            }
        });

        // Klik tombol 3 bulan
        prem3Bulan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveButton(prem3Bulan, "Berlangganan 3 Bulan", R.drawable.kotak_prem_biru);
                setInactiveButton(prem1Bulan, R.drawable.kotak_prem_abu);
                setInactiveButton(prem6Bulan, R.drawable.kotak_prem_abu);
            }
        });

        // Klik tombol 6 bulan
        prem6Bulan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveButton(prem6Bulan, "Berlangganan 6 Bulan", R.drawable.kotak_prem_pink);
                setInactiveButton(prem1Bulan, R.drawable.kotak_prem_abu);
                setInactiveButton(prem3Bulan, R.drawable.kotak_prem_abu);
            }
        });
    }

    // Method untuk mengaktifkan tombol
    private void setActiveButton(Button button, String textLangganan, int backgroundResId) {
        button.setBackgroundResource(backgroundResId);
        berlangganan.setText(textLangganan);
    }

    // Method untuk menonaktifkan tombol
    private void setInactiveButton(Button button, int backgroundResId) {
        button.setBackgroundResource(backgroundResId);
    }

    public void kembaliKeHome(View view) {
        Intent intent = new Intent(this, HomeFoodyActivity.class);
        startActivity(intent);
    }
}
