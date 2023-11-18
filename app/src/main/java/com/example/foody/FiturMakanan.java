package com.example.foody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.HorizontalScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FiturMakanan extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private MakananAdapter makananAdapter;
    private List<MakananModel> makananList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitur_makanan);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() { // Ganti menjadi setOnItemSelectedListener
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    // Buka halaman HomeFoodyActivity
                    Intent intent = new Intent(FiturMakanan.this, HomeFoodyActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.nav_catatanku) {
                    // Buka halaman activity_fitur_catatanku
                    Intent intent = new Intent(FiturMakanan.this, fitur_catatanku.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.nav_kalkulator) {
                    // Buka halaman KalkulatorBmi
                    Intent intent = new Intent(FiturMakanan.this, KalkulatorBmi.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.nav_makanan) {
                    // Buka halaman FiturMakanan
                    return true;
                } else if (item.getItemId() == R.id.nav_profil) {
                    // Buka halaman FiturProfil
                    Intent intent = new Intent(FiturMakanan.this, FiturProfil.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
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

        // Konfigurasi RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(makananAdapter);

        // Panggil method untuk mengambil data dari API
        getDataMakananFromApi();

        // Cari SearchView berdasarkan ID
        SearchView searchView = findViewById(R.id.search_makanan);

        TextView txtKandunganMakanan = findViewById(R.id.txt_kandungan_makanan);
        HorizontalScrollView hrScroll = findViewById(R.id.hr_scroll);

        // Atur listener pencarian
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Pencarian selesai (mis., ketika pengguna menekan tombol "Enter" pada keyboard)
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Panggil metode untuk menyaring data berdasarkan teks pencarian
                filterMakanan(newText);
                // Ubah visibilitas elemen-elemen sesuai kebutuhan
                if (newText.isEmpty()) {
                    // Teks pencarian kosong, tampilkan kembali elemen-elemen yang disembunyikan
                    txtKandunganMakanan.setVisibility(View.VISIBLE);
                    hrScroll.setVisibility(View.VISIBLE);
                } else {
                    // Teks pencarian tidak kosong, sembunyikan elemen-elemen
                    txtKandunganMakanan.setVisibility(View.GONE);
                    hrScroll.setVisibility(View.GONE);
                }

                return true;
            }
        });

    }
    private void getDataMakananFromApi() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<ApiResponse<List<MakananModel>>> call = apiService.getMakanan();
        call.enqueue(new Callback<ApiResponse<List<MakananModel>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<MakananModel>>> call, Response<ApiResponse<List<MakananModel>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<MakananModel>> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("success")) {
                        List<MakananModel> makananModels = apiResponse.getData();
                        if (makananModels != null && makananModels.size() > 0) {
                            makananList.addAll(makananModels);
                            makananAdapter.notifyDataSetChanged();
                        } else {
                            // Tampilkan pesan bahwa tidak ada data makanan
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

        // Setel filteredList sebagai data untuk RecyclerView
        makananAdapter.filterList(filteredList);
    }

//    private void loadMakananData(String makananId) {
//        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
//        Call<ApiResponse<MakananModel>> call = apiService.getDetailMakanan(makananId);
//        call.enqueue(new Callback<ApiResponse<MakananModel>>() {
//            @Override
//            public void onResponse(Call<ApiResponse<MakananModel>> call, Response<ApiResponse<MakananModel>> response) {
//                if (response.isSuccessful()) {
//                    ApiResponse<MakananModel> apiResponse = response.body();
//                    if (apiResponse != null && apiResponse.getStatus().equals("success")) {
//                        MakananModel makananModel = apiResponse.getData();
//                        if (makananModel != null) {
//                            // Lakukan sesuatu dengan data makananModel
//                            // Contoh: Setel data ke tampilan
//                            setMakananDataToViews(makananModel);
//                        } else {
//                            // Tampilkan pesan bahwa tidak ada data makanan
//                            Toast.makeText(FiturMakanan.this, "Tidak ada data makanan", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        // Tampilkan pesan bahwa respons tidak sukses
//                        Toast.makeText(FiturMakanan.this, "Respons tidak sukses", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    // Tampilkan pesan bahwa terjadi kesalahan saat mengambil data makanan
//                    Toast.makeText(FiturMakanan.this, "Gagal mengambil data makanan", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse<MakananModel>> call, Throwable t) {
//                // Tampilkan pesan bahwa terjadi kesalahan jaringan
//                Toast.makeText(FiturMakanan.this, "Terjadi kesalahan jaringan", Toast.LENGTH_SHORT).show();
//                Log.e("FiturMakanan", "Kesalahan jaringan", t);
//            }
//        });
//    }
//
//    private void setMakananDataToViews(MakananModel makananModel) {
//        ImageView imageViewMakanan = findViewById(R.id.gambar_makanan); // Sesuaikan dengan ID ImageView pada layout XML Anda
//        TextView textViewNamaMakanan = findViewById(R.id.nm_data); // Sesuaikan dengan ID TextView untuk nama makanan pada layout XML Anda
////        TextView textViewDeskripsiMakanan = findViewById(R.id.text_deskripsi_makanan);
//        TextView textKarbohidrat = findViewById(R.id.hasil_carbo); // Sesuaikan dengan ID TextView untuk hasil karbohidrat pada layout XML Anda
//        TextView textProtein = findViewById(R.id.hasil_protein); // Sesuaikan dengan ID TextView untuk hasil protein pada layout XML Anda
//        TextView textGaram = findViewById(R.id.hasil_garam); // Sesuaikan dengan ID TextView untuk hasil garam pada layout XML Anda
//        TextView textGula = findViewById(R.id.hasil_gula); // Sesuaikan dengan ID TextView untuk hasil gula pada layout XML Anda
//        TextView textLemak = findViewById(R.id.hasil_lemak); // Sesuaikan dengan ID TextView untuk hasil lemak pada layout XML Anda
//
//        // Setel nilai ke elemen antarmuka pengguna
//        textViewNamaMakanan.setText(makananModel.getNama());
////        textViewDeskripsiMakanan.setText(makananModel.getDeskripsi());
//
//        // Setel nilai ke TextView hasil karbohidrat, protein, garam, gula, lemak sesuai dengan respons API
//        textKarbohidrat.setText(String.valueOf(makananModel.getKarbohidrat()));
//        textProtein.setText(String.valueOf(makananModel.getProtein()));
//        textGaram.setText(String.valueOf(makananModel.getGaram()));
//        textGula.setText(String.valueOf(makananModel.getGula()));
//        textLemak.setText(String.valueOf(makananModel.getLemak()));
//
//        // Load gambar menggunakan Glide ke ImageView
//        Glide.with(this)
//                .load(makananModel.getGambar())
//                .into(imageViewMakanan);
//    }

//    private void loadMakananData(String makananId) {
//        // Memanggil metode loadMakananData dari kelas utilitas
//        NetworkUtils.loadMakananData(makananId, this);
//    }



}

// Inisialisasi RecyclerView
//        recyclerView = findViewById(R.id.rvmakanan);
//                recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//                // Inisialisasi data makanan
//                makananList = new ArrayList<>();
//        makananList.add(new MakananModel(1, "Baked Sweet Potato", "Karbohidrat", 50, 0, 163, "Protein", 75, 0, 60, "Gula", 25, 0, 60, "Lemak", 60, 0, 60, "Garam", 60, 0, 60, R.drawable.sweet_potato));
//        makananList.add(new MakananModel(2, "Another Food Item", "Karbohidrat", 30, 0, 123, "Protein", 60, 0, 50, "Gula", 15, 0, 55, "Lemak", 40, 0, 40, "Garam", 50, 0, 50, R.drawable.sweet_potato));
//        makananList.add(new MakananModel(3, "Yet Another Food", "Karbohidrat", 45, 0, 156, "Protein", 80, 0, 70, "Gula", 35, 0, 67, "Lemak", 55, 0, 55, "Garam", 70, 0, 80, R.drawable.sweet_potato));
//        // Tambahkan data makanan lainnya sesuai kebutuhan
//
//        RecyclerView recyclerView = findViewById(R.id.rvmakanan);
//        MakananAdapter adapter = new MakananAdapter(makananList);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

