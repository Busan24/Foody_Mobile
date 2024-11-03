package com.example.foody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.HorizontalScrollView;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import android.graphics.drawable.ColorDrawable;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FiturMakanan extends AdsActivity {
    BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private MakananAdapter makananAdapter;
    private List<MakananModel> makananList;
    private Button tinggiKarbohidrat, tinggiProtein, tinggiLemak, rendahKarbohidrat, rendahProtein, rendahLemak;
    private Button reset;
    private Button generateMakanan;
    private LinearLayout illustrasiEmpty;

    private String makananDicari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_fitur_makanan, findViewById(R.id.content_frame));
//        setContentView(R.layout.activity_fitur_makanan);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        tinggiKarbohidrat = findViewById(R.id.tinggi_karbohidrat);
        tinggiProtein   =  findViewById(R.id.tinggi_protein);
        tinggiLemak = findViewById(R.id.tinggi_lemak);
        rendahKarbohidrat = findViewById(R.id.rendah_karbohidrat);
        rendahProtein   =  findViewById(R.id.rendah_protein);
        rendahLemak = findViewById(R.id.rendah_lemak);
        illustrasiEmpty = findViewById(R.id.illustrasi_empty);
        generateMakanan = findViewById(R.id.generate_makanan);

        reset = findViewById(R.id.restart);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBackground();
                reset.setVisibility(View.GONE);
                getDataMakananFromApi("");
            }
        });

        tinggiKarbohidrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBackground();
                tinggiKarbohidrat.setBackgroundResource(R.drawable.rounded_button_border);
                getDataMakananFromApi("tinggi-karbohidrat");
            }
        });

        rendahKarbohidrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBackground();
                rendahKarbohidrat.setBackgroundResource(R.drawable.rounded_button_border);
                getDataMakananFromApi("rendah-karbohidrat");
            }
        });

        tinggiProtein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBackground();
                tinggiProtein.setBackgroundResource(R.drawable.rounded_button_border);
                getDataMakananFromApi("tinggi-protein");
            }
        });

        rendahProtein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBackground();
                rendahProtein.setBackgroundResource(R.drawable.rounded_button_border);
                getDataMakananFromApi("rendah-protein");
            }
        });

        tinggiLemak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBackground();
                tinggiLemak.setBackgroundResource(R.drawable.rounded_button_border);
                getDataMakananFromApi("tinggi-lemak");
            }
        });

        rendahLemak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBackground();
                rendahLemak.setBackgroundResource(R.drawable.rounded_button_border);
                getDataMakananFromApi("rendah-lemak");
            }
        });


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() { // Ganti menjadi setOnItemSelectedListener
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    // Buka halaman HomeFoodyActivity
                    Intent intent = new Intent(FiturMakanan.this, HomeFoodyActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                } else if (item.getItemId() == R.id.nav_catatanku) {
                    // Buka halaman activity_fitur_catatanku
                    Intent intent = new Intent(FiturMakanan.this, fitur_catatanku.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                } else if (item.getItemId() == R.id.nav_kalkulator) {
                    // Buka halaman KalkulatorBmi
                    Intent intent = new Intent(FiturMakanan.this, KalkulatorBmi.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                } else if (item.getItemId() == R.id.nav_makanan) {
                    // Buka halaman FiturMakanan
                    return true;
                } else if (item.getItemId() == R.id.nav_profil) {
                    // Buka halaman FiturProfil
                    Intent intent = new Intent(FiturMakanan.this, FiturProfil.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
                return true;
            }

        });


        recyclerView = findViewById(R.id.rvmakanan);
        makananList = new ArrayList<>();
        makananAdapter = new MakananAdapter(makananList);

        makananAdapter.setOnItemClickListener(new MakananAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (makananAdapter != null && makananAdapter.getItemCount() > position) {
                    // Pastikan adapter tidak null dan memiliki item di dalamnya
                    String idMakanan = makananAdapter.getItem(position).getId();
                    Intent intent = new Intent(FiturMakanan.this, DataMakanan.class);
                    intent.putExtra("ID_MAKANAN", idMakanan);
                    startActivity(intent);
                } else {
                    Log.e("FiturMakanan", "Adapter is null or empty");
                    // Tampilkan pesan kesalahan jika terjadi masalah
                    Toast.makeText(FiturMakanan.this, "Terjadi kesalahan saat mengambil data makanan", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Cari SearchView berdasarkan ID
        SearchView searchView = findViewById(R.id.search_makanan);
        searchView.setQueryHint("Cari makanan...");

        generateMakanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getPremiumStatus()) {
                    showPremiumDialog();
                    return;
                }
                if (!makananDicari.isEmpty()) {
                    generateMakanan(makananDicari);
                }
            }
        });

        // Konfigurasi RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(makananAdapter);

        // Panggil method untuk mengambil data dari API
        getDataMakananFromApi("");

//        TextView txtKandunganMakanan = findViewById(R.id.txt_kandungan_makanan);
//        HorizontalScrollView hrScroll = findViewById(R.id.hr_scroll);

        // Atur listener pencarian
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Pencarian selesai (mis., ketika pengguna menekan tombol "Enter" pada keyboard)
                makananDicari = query;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Panggil metode untuk menyaring data berdasarkan teks pencarian
                filterMakanan(newText);
                makananDicari = newText;
                // Ubah visibilitas elemen-elemen sesuai kebutuhan
//                if (newText.isEmpty()) {
//                    // Teks pencarian kosong, tampilkan kembali elemen-elemen yang disembunyikan
//                    txtKandunganMakanan.setVisibility(View.VISIBLE);
//                    hrScroll.setVisibility(View.VISIBLE);
//                } else {
//                    // Teks pencarian tidak kosong, sembunyikan elemen-elemen
//                    txtKandunganMakanan.setVisibility(View.GONE);
//                    hrScroll.setVisibility(View.GONE);
//                }

                return true;
            }
        });

    }

    private void resetBackground()  {
        reset.setVisibility(View.VISIBLE);
        tinggiKarbohidrat.setBackgroundResource(R.drawable.rounded_button);
        tinggiProtein.setBackgroundResource(R.drawable.rounded_button);
        tinggiLemak.setBackgroundResource(R.drawable.rounded_button);
        rendahKarbohidrat.setBackgroundResource(R.drawable.rounded_button);
        rendahProtein.setBackgroundResource(R.drawable.rounded_button);
        rendahLemak.setBackgroundResource(R.drawable.rounded_button);
    }

    private void getDataMakananFromApi(String kategori) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<ApiResponse<List<MakananModel>>> call = apiService.getMakanan("Bearer " + getAuthToken(), kategori);
        call.enqueue(new Callback<ApiResponse<List<MakananModel>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<MakananModel>>> call, Response<ApiResponse<List<MakananModel>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<MakananModel>> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("success")) {
                        List<MakananModel> makananModels = apiResponse.getData();
                        makananList.clear();
                        if (makananModels != null && makananModels.size() > 0) {
                            makananList.addAll(makananModels);
                            makananAdapter.notifyDataSetChanged();
                        } else {
                            // Tampilkan pesan bahwa tidak ada data makanan
                            makananAdapter.notifyDataSetChanged();
                            Toast.makeText(FiturMakanan.this, "Tidak ada data makanan", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Tampilkan pesan bahwa respons tidak sukses
                        Toast.makeText(FiturMakanan.this, "Respons tidak sukses", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Tampilkan pesan bahwa terjadi kesalahan saat mengambil data makanan
                    Toast.makeText(FiturMakanan.this, "Gagal mengambil data makanan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<MakananModel>>> call, Throwable t) {
                // Tampilkan pesan bahwa terjadi kesalahan jaringan
                Toast.makeText(FiturMakanan.this, "Terjadi kesalahan jaringan", Toast.LENGTH_SHORT).show();
                Log.e("FiturMakanan", "Kesalahan jaringan", t);
            }

        });
    }

    private void filterMakanan(String searchText) {
        List<MakananModel> filteredList = new ArrayList<>();

        // Loop melalui makananList dan tambahkan yang sesuai dengan teks pencarian ke filteredList
        for (MakananModel makanan : makananList) {
            if (makanan.getNama().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(makanan);
            }
        }

        if (filteredList.isEmpty()) {
            illustrasiEmpty.setVisibility(View.VISIBLE);
        }

        else {
            illustrasiEmpty.setVisibility(View.GONE);
        }

        // Setel filteredList sebagai data untuk RecyclerView
        makananAdapter.filterList(filteredList);
    }

    private void generateMakanan(String makanan) {

        Dialog loadingDialog = new Dialog(FiturMakanan.this);
        loadingDialog.setContentView(R.layout.dialog_loading);

        // Mengatur latar belakang dialog agar transparan
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Window window = loadingDialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }

        // Menampilkan loading dialog
        loadingDialog.show();

        GenerateMakananRequestModel generateMakananRequestModel = new GenerateMakananRequestModel(makanan);
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<ApiResponse<MakananModel>> call = apiService.createMakanan("Bearer " + getAuthToken(), generateMakananRequestModel);
        call.enqueue(new Callback<ApiResponse<MakananModel>>() {
            @Override
            public void onResponse(Call<ApiResponse<MakananModel>> call, Response<ApiResponse<MakananModel>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<MakananModel> apiResponse = response.body();
                    MakananModel dataMakanan = apiResponse.getData();

                    makananList.add(dataMakanan);
                    makananAdapter.notifyDataSetChanged();
                    filterMakanan(makanan);

                    String idMakanan = dataMakanan.getId();
                    Intent intent = new Intent(FiturMakanan.this, DataMakanan.class);
                    intent.putExtra("ID_MAKANAN", idMakanan);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(FiturMakanan.this, "Makanan tidak ditemukan. Periksa kembali nama makanan", Toast.LENGTH_SHORT).show();
                }

                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ApiResponse<MakananModel>> call, Throwable t) {
                Toast.makeText(FiturMakanan.this, "Terjadi kesalaha. Perika koneksi internet anda.", Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
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

