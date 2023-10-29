package com.example.foody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class fitur_catatanku extends AppCompatActivity {
    Dialog myDialog;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitur_catatanku);
        myDialog = new Dialog(this);

        // Inisialisasi button btn_nyatat
        Button btnNyatat = findViewById(R.id.btn_nyatat);
        btnNyatat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog("PAGI", "#FDCED0"); // Teks "PAGI" dan latar belakang pink
            }
        });

        Button btnNyatatSiang = findViewById(R.id.btn_nyatet_siang);
        btnNyatatSiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog("SIANG", "#D9F4FF"); // Teks "SIANG" dan latar belakang biru cerah
            }
        });

        Button btnNyatatSore = findViewById(R.id.btn_nyatet_sore);
        btnNyatatSore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog("SORE", "#FDCED0");
            }
        });

        Button btnNyatatMalam = findViewById(R.id.btn_nyatet_malam);
        btnNyatatMalam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog("MALAM", "#D9F4FF");
            }
        });

            Button btnHistory = findViewById(R.id.btn_hs_catatan);
            btnHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(fitur_catatanku.this, HistoryCatatnkuActivity.class);
                    startActivity(intent);
                }
            });

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() { // Ganti menjadi setOnItemSelectedListener
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    // Buka halaman HomeFoodyActivity
                    Intent intent = new Intent(fitur_catatanku.this, HomeFoodyActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.nav_catatanku) {
                    // Buka halaman activity_fitur_catatanku
                    return true;
                } else if (item.getItemId() == R.id.nav_kalkulator) {
                    // Buka halaman KalkulatorBmi
                    Intent intent = new Intent(fitur_catatanku.this, KalkulatorBmi.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.nav_makanan) {
                    // Buka halaman FiturMakanan
                    Intent intent = new Intent(fitur_catatanku.this, FiturMakanan.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.nav_profil) {
                    // Buka halaman FiturProfil
                    Intent intent = new Intent(fitur_catatanku.this, FiturProfil.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
                return true;
            }

        });

    }

    private void showCustomDialog(String text, String backgroundColor) {
        // Membuat dialog
        myDialog.setContentView(R.layout.popup_catatanku);

        // Mengakses elemen-elemen dalam dialog
        LinearLayout bgPopup = myDialog.findViewById(R.id.bg_popup);
        TextView txtNyatatHari = myDialog.findViewById(R.id.txt_nyatat_hari);

        // Mengubah teks dan latar belakang sesuai dengan tombol yang ditekan
        txtNyatatHari.setText(text);
        bgPopup.setBackgroundColor(Color.parseColor(backgroundColor));

        // Inisialisasi Spinner waktu_nyatat sesuai dengan waktu yang diinginkan
        Spinner spinnerWaktuNyatat = myDialog.findViewById(R.id.waktu_nyatat);
        ArrayAdapter<String> waktuAdapter = null;

        if (text.equals("PAGI")) {
            waktuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.waktu_entries));
        } else if (text.equals("SIANG")) {
            waktuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.waktu_entries_siang));
        } else if (text.equals("SORE")) {
            waktuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.waktu_entries_sore));
        } else if (text.equals("MALAM")) {
            waktuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.waktu_entries_malam));
        }

        waktuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWaktuNyatat.setAdapter(waktuAdapter);

        // Inisialisasi Spinner jmlh_porsi sesuai dengan waktu yang dipilih
        Spinner spinnerJmlhPorsi = myDialog.findViewById(R.id.jmlh_porsi);
        ArrayAdapter<String> jmlhPorsiAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.jmlh_porsi_entries));
        jmlhPorsiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJmlhPorsi.setAdapter(jmlhPorsiAdapter);

        // Atur atribut dialog (opsional)
        Window window = myDialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Tampilkan dialog
        myDialog.show();
    }


}
