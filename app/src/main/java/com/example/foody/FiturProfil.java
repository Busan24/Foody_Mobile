package com.example.foody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ViewSwitcher;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Calendar;

public class FiturProfil extends AppCompatActivity {

    Dialog myDialog;
    private ViewSwitcher summarySwitcher;
    private Button btnAccount;
    private Button btnSummary;
    private int btnAccountTextColor;
    private int btnSummaryTextColor;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitur_profil);
        myDialog = new Dialog(this);

        Button btnEpProfile = findViewById(R.id.btn_edit_profile);
        summarySwitcher = findViewById(R.id.summary_switcher);
        btnAccount = findViewById(R.id.btn_Account);
        btnSummary = findViewById(R.id.btn_summary);

        // Inisialisasi warna teks
        btnAccountTextColor = ContextCompat.getColor(this, R.color.birucerah); // Warna teks untuk btn_Account
        btnSummaryTextColor = ContextCompat.getColor(this, R.color.birutua); // Warna teks untuk btn_Summary

        btnEpProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil metode showCustomDialog() ketika tombol btn_edit_profile ditekan
                showCustomDialog();
            }
        });

        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cek apakah sedang berada di tampilan activity_switch_akun
                if (summarySwitcher.getCurrentView() == summarySwitcher.findViewById(R.id.switch_akun)) {
                    // Tidak lakukan apa-apa karena sedang di tampilan activity_switch_akun
                } else {
                    // Beralih ke tampilan dari activity_switch_akun.xml
                    summarySwitcher.showNext();
                    btnAccount.setBackgroundResource(R.drawable.pp_oval_birutua);
                    btnAccount.setTextColor(btnAccountTextColor);
                    btnSummary.setBackgroundResource(R.drawable.pp_oval_birucerah);
                    btnSummary.setTextColor(btnSummaryTextColor);
                }
            }
        });

        btnSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cek apakah sedang berada di tampilan halaman_summary
                if (summarySwitcher.getCurrentView() == summarySwitcher.findViewById(R.id.halaman_summary)) {
                    // Tidak lakukan apa-apa karena sedang di tampilan halaman_summary
                } else {
                    // Beralih kembali ke tampilan pertama (Summary)
                    summarySwitcher.showPrevious();
                    btnAccount.setBackgroundResource(R.drawable.pp_oval_birucerah);
                    btnAccount.setTextColor(btnSummaryTextColor);
                    btnSummary.setBackgroundResource(R.drawable.pp_oval_birutua);
                    btnSummary.setTextColor(btnAccountTextColor);
                }
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() { // Ganti menjadi setOnItemSelectedListener
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    // Buka halaman HomeFoodyActivity
                    Intent intent = new Intent(FiturProfil.this, HomeFoodyActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.nav_catatanku) {
                    // Buka halaman activity_fitur_catatanku
                    Intent intent = new Intent(FiturProfil.this, fitur_catatanku.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.nav_kalkulator) {
                    // Buka halaman KalkulatorBmi
                    Intent intent = new Intent(FiturProfil.this, KalkulatorBmi.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.nav_makanan) {
                    // Buka halaman FiturMakanan
                    Intent intent = new Intent(FiturProfil.this, FiturMakanan.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.nav_profil) {
                    // Buka halaman FiturProfil
                    return true;
                }
                return true;
            }

        });
    }

    private void showCustomDialog() {
        myDialog.setContentView(R.layout.popup_edit_profile);

        Window window = myDialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Tampilkan dialog
        myDialog.show();
    }

    public void showDatePickerDialog(View view) {
        final EditText dateEditText = (EditText) view; // Mengonversi View menjadi EditText

        // Mendapatkan tanggal sekarang
        int year, month, day;
        String dateText = dateEditText.getText().toString();
        if (dateText.isEmpty()) {
            // Jika EditText kosong, gunakan tanggal sekarang
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        } else {
            // Jika EditText sudah memiliki tanggal, gunakan tanggal yang ada
            String[] dateParts = dateText.split("-");
            year = Integer.parseInt(dateParts[0]);
            month = Integer.parseInt(dateParts[1]) - 1;
            day = Integer.parseInt(dateParts[2]);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                // Menangani pemilihan tanggal
                String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                dateEditText.setText(selectedDate);
            }
        }, year, month, day);

        datePickerDialog.show();
    }
}