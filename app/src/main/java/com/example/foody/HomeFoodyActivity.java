package com.example.foody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFoodyActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    ViewSwitcher switcher;
    private TextView namaAkun;
    private String authToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_foody);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        ImageView profilHome = findViewById(R.id.profilhome);

        namaAkun = findViewById(R.id.username_home);

        authToken = getAuthToken();

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        // Melakukan permintaan profil pengguna dengan menyertakan token
        Call<UserProfile> call = apiService.getUserProfile("Bearer " + authToken);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful()) {
                    UserProfile userProfile = response.body();
                    UserData userData = userProfile.getData();

                    namaAkun.setText(userData.getName());
                } else {
                    // Tangani kesalahan pada respons
                    Toast.makeText(HomeFoodyActivity.this, "Gagal mengambil profil. Coba lagi.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                // Tangani kesalahan pada permintaan
                Toast.makeText(HomeFoodyActivity.this, "Gagal mengambil profil. Periksa koneksi internet Anda.", Toast.LENGTH_SHORT).show();
                Log.e("Get Profile Error", "Gagal mengambil profil", t);
            }
        });

        profilHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeFoodyActivity.this, FiturProfil.class);
                startActivity(intent);
            }
        });

        TextView viewAllTextView = findViewById(R.id.viewall_produk);

        viewAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeFoodyActivity.this, RekomendasiProduk.class);
                startActivity(intent);
            }
        });

        ImageView arrowAllProduk = findViewById(R.id.vall_produk);

        arrowAllProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeFoodyActivity.this, RekomendasiProduk.class);
                startActivity(intent);
            }
        });


        // Membuat dialog kustom
        final Dialog customDialog = new Dialog(this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.activity_dialog_rekommakanan);

        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Mendapatkan referensi elemen "see_details" dan "arrow_rekom"
        TextView seeDetails = findViewById(R.id.see_details);
        ImageView arrowRekom = findViewById(R.id.arrow_rekom);

        // Menambahkan OnClickListener pada "see_details"
        seeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tampilkan dialog kustom ketika "see_details" diklik

                // Mendapatkan posisi elemen "see_details" pada layar
                int[] location = new int[2];
                v.getLocationOnScreen(location);

                // Menentukan posisi vertikal dialog berdasarkan elemen "see_details"
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(customDialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; // Mengatur lebar dialog menjadi full layar
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                layoutParams.gravity = Gravity.TOP | Gravity.START;
                layoutParams.x = location[0]; // Mengikuti posisi horizontal elemen "see_details"
                layoutParams.y = location[1] + v.getHeight(); // Menampilkan dialog di bawah elemen "see_details"

                customDialog.getWindow().setAttributes(layoutParams);
                customDialog.show();
            }
        });

        // Menambahkan OnClickListener pada "arrow_rekom"
        arrowRekom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tampilkan dialog kustom ketika "arrow_rekom" diklik

                // Mendapatkan posisi elemen "arrow_rekom" pada layar
                int[] location = new int[2];
                v.getLocationOnScreen(location);

                // Menentukan posisi vertikal dialog berdasarkan elemen "arrow_rekom"
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(customDialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; // Mengatur lebar dialog menjadi full layar
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                layoutParams.gravity = Gravity.TOP | Gravity.START;
                layoutParams.x = location[0]; // Mengikuti posisi horizontal elemen "arrow_rekom"
                layoutParams.y = location[1] + v.getHeight(); // Menampilkan dialog di bawah elemen "arrow_rekom"

                customDialog.getWindow().setAttributes(layoutParams);
                customDialog.show();
            }
        });


//        // Menutup dialog saat tombol "Close" pada dialog ditekan
//        Button closeButton = customDialog.findViewById(R.id.btn_close);
//        if (closeButton != null) {
//            closeButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    customDialog.dismiss();
//                }
//            });
//        }

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() { // Ganti menjadi setOnItemSelectedListener
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    // Buka halaman HomeFoodyActivity
                    return true;
                } else if (item.getItemId() == R.id.nav_catatanku) {
                    // Buka halaman activity_fitur_catatanku
                    Intent intent = new Intent(HomeFoodyActivity.this, fitur_catatanku.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.nav_kalkulator) {
                    // Buka halaman KalkulatorBmi
                    Intent intent = new Intent(HomeFoodyActivity.this, KalkulatorBmi.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.nav_makanan) {
                    // Buka halaman FiturMakanan
                    Intent intent = new Intent(HomeFoodyActivity.this, FiturMakanan.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.nav_profil) {
                    // Buka halaman FiturProfil
                    Intent intent = new Intent(HomeFoodyActivity.this, FiturProfil.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
                return true;
            }

        });

        switcher = findViewById(R.id.switcher_dharian); // Inisialisasi ViewSwitcher

        Button btnHariSenen = findViewById(R.id.btn_hari_senen);
        Button btnHariSelasa = findViewById(R.id.btn_hari_selasa);
        Button btnHariRabu = findViewById(R.id.btn_hari_rabu);
        Button btnHariKamis = findViewById(R.id.btn_hari_kamis);
        Button btnHariJumat = findViewById(R.id.btn_hari_jumat);
        Button btnHariSabtu = findViewById(R.id.btn_hari_sabtu);
        Button btnHariMinggu = findViewById(R.id.btn_hari_minggu);

        btnHariSenen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    switcher.showNext();
                    btnHariSenen.setBackgroundResource(R.drawable.btn_date_home);
                    btnHariSelasa.setBackgroundResource(R.drawable.btn_bulat_putih);
                    btnHariRabu.setBackgroundResource(R.drawable.btn_bulat_putih);
                    btnHariKamis.setBackgroundResource(R.drawable.btn_bulat_putih);
                    btnHariJumat.setBackgroundResource(R.drawable.btn_bulat_putih);
                    btnHariSabtu.setBackgroundResource(R.drawable.btn_bulat_putih);
                    btnHariMinggu.setBackgroundResource(R.drawable.btn_bulat_putih);
                }
        });

        btnHariSelasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    switcher.showNext();
                    btnHariSelasa.setBackgroundResource(R.drawable.btn_date_home);
                    btnHariSenen.setBackgroundResource(R.drawable.btn_bulat_putih);
                    btnHariRabu.setBackgroundResource(R.drawable.btn_bulat_putih);
                    btnHariKamis.setBackgroundResource(R.drawable.btn_bulat_putih);
                    btnHariJumat.setBackgroundResource(R.drawable.btn_bulat_putih);
                    btnHariSabtu.setBackgroundResource(R.drawable.btn_bulat_putih);
                    btnHariMinggu.setBackgroundResource(R.drawable.btn_bulat_putih);
                }
        });

        btnHariRabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    switcher.showNext();
                    btnHariSelasa.setBackgroundResource(R.drawable.btn_bulat_putih);
                    btnHariSenen.setBackgroundResource(R.drawable.btn_bulat_putih);
                    btnHariRabu.setBackgroundResource(R.drawable.btn_date_home);
                    btnHariKamis.setBackgroundResource(R.drawable.btn_bulat_putih);
                    btnHariJumat.setBackgroundResource(R.drawable.btn_bulat_putih);
                    btnHariSabtu.setBackgroundResource(R.drawable.btn_bulat_putih);
                    btnHariMinggu.setBackgroundResource(R.drawable.btn_bulat_putih);
                }
        });

        btnHariKamis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switcher.showNext();
                btnHariSelasa.setBackgroundResource(R.drawable.btn_bulat_putih);
                btnHariSenen.setBackgroundResource(R.drawable.btn_bulat_putih);
                btnHariRabu.setBackgroundResource(R.drawable.btn_bulat_putih);
                btnHariKamis.setBackgroundResource(R.drawable.btn_date_home);
                btnHariJumat.setBackgroundResource(R.drawable.btn_bulat_putih);
                btnHariSabtu.setBackgroundResource(R.drawable.btn_bulat_putih);
                btnHariMinggu.setBackgroundResource(R.drawable.btn_bulat_putih);
            }
        });

        btnHariJumat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switcher.showNext();
                btnHariSelasa.setBackgroundResource(R.drawable.btn_bulat_putih);
                btnHariSenen.setBackgroundResource(R.drawable.btn_bulat_putih);
                btnHariRabu.setBackgroundResource(R.drawable.btn_bulat_putih);
                btnHariKamis.setBackgroundResource(R.drawable.btn_bulat_putih);
                btnHariJumat.setBackgroundResource(R.drawable.btn_date_home);
                btnHariSabtu.setBackgroundResource(R.drawable.btn_bulat_putih);
                btnHariMinggu.setBackgroundResource(R.drawable.btn_bulat_putih);
            }
        });

        btnHariSabtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switcher.showNext();
                btnHariSelasa.setBackgroundResource(R.drawable.btn_bulat_putih);
                btnHariSenen.setBackgroundResource(R.drawable.btn_bulat_putih);
                btnHariRabu.setBackgroundResource(R.drawable.btn_bulat_putih);
                btnHariKamis.setBackgroundResource(R.drawable.btn_bulat_putih);
                btnHariJumat.setBackgroundResource(R.drawable.btn_bulat_putih);
                btnHariSabtu.setBackgroundResource(R.drawable.btn_date_home);
                btnHariMinggu.setBackgroundResource(R.drawable.btn_bulat_putih);
            }
        });

        btnHariMinggu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switcher.showNext();
                btnHariSelasa.setBackgroundResource(R.drawable.btn_bulat_putih);
                btnHariSenen.setBackgroundResource(R.drawable.btn_bulat_putih);
                btnHariRabu.setBackgroundResource(R.drawable.btn_bulat_putih);
                btnHariKamis.setBackgroundResource(R.drawable.btn_bulat_putih);
                btnHariJumat.setBackgroundResource(R.drawable.btn_bulat_putih);
                btnHariSabtu.setBackgroundResource(R.drawable.btn_bulat_putih);
                btnHariMinggu.setBackgroundResource(R.drawable.btn_date_home);
            }
        });
    }

    private String getAuthToken() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("auth_token", MODE_PRIVATE);
        String authToken = sharedPreferences.getString("token", "");
        Log.d("AuthToken", "Token: " + authToken); // Tambahkan log ini
        return authToken;
    }
}
