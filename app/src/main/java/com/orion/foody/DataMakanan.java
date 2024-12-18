package com.orion.foody;

//import static com.example.foody.NetworkUtils.loadMakananData;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataMakanan extends AdsActivity {

    private TextView namaMakananTextView;
    private TextView karbohidratTextView;
    private TextView proteinTextView;
    private TextView gulaTextView;
    private TextView lemakTextView;
    private TextView garamTextView;
    private ImageView gambarMakananImageView;
    private String authToken;

    private TextView batasKarbohidrat, batasProtein, batasGula, batasLemak, batasGaram;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_data_makanan, findViewById(R.id.content_frame));
//        setContentView(R.layout.activity_data_makanan);

        // Inisialisasi elemen UI
        namaMakananTextView = findViewById(R.id.nm_data);
        karbohidratTextView = findViewById(R.id.hasil_carbo);
        proteinTextView = findViewById(R.id.hasil_protein);
        gulaTextView = findViewById(R.id.hasil_gula);
        lemakTextView = findViewById(R.id.hasil_lemak);
        garamTextView = findViewById(R.id.hasil_garam);
        gambarMakananImageView = findViewById(R.id.gambar_makanan);

        // Summary Data Batas kandungan
        batasKarbohidrat = findViewById(R.id.batas_karbohidrat);
        batasProtein = findViewById(R.id.batas_protein);
        batasGula = findViewById(R.id.batas_gula);
        batasLemak = findViewById(R.id.batas_lemak);
        batasGaram = findViewById(R.id.batas_garam);

        authToken = getAuthToken();

        // ...

        // Ambil ID makanan dari intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("ID_MAKANAN")) {
            String idMakanan = intent.getStringExtra("ID_MAKANAN");

            // Panggil metode untuk mengambil data makanan berdasarkan ID dari API
            getDataMakananAndSummary(idMakanan);
        } else {
            // Handle jika ID makanan tidak ditemukan
            Toast.makeText(this, "ID Makanan tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish(); // Selesaikan activity jika tidak ada ID makanan
        }
    }

    // Metode untuk mengambil data makanan berdasarkan ID dari API
    private void getDataMakananAndSummary(String idMakanan) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        // Panggil dua request API secara bersamaan menggunakan enqueue
        Call<ApiResponse<MakananModel>> callMakanan = apiService.getMakananById("Bearer " + authToken, idMakanan);
        Call<ApiResponse<SummaryData>> callSummary = apiService.getUserSummary("Bearer " + authToken);

        // Menggunakan `enqueue` secara bersamaan untuk mendapatkan hasil secara simultan
        callMakanan.enqueue(new Callback<ApiResponse<MakananModel>>() {
            @Override
            public void onResponse(Call<ApiResponse<MakananModel>> call, Response<ApiResponse<MakananModel>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<MakananModel> apiResponse = response.body();
                    if (apiResponse != null && "success".equals(apiResponse.getStatus())) {
                        MakananModel makanan = apiResponse.getData();
                        if (makanan != null) {
                            // Panggil metode untuk mengambil data summary
                            callSummary.enqueue(new Callback<ApiResponse<SummaryData>>() {
                                @Override
                                public void onResponse(Call<ApiResponse<SummaryData>> call, Response<ApiResponse<SummaryData>> response) {
                                    if (response.isSuccessful()) {
                                        ApiResponse<SummaryData> apiResponseSummary = response.body();
                                        if (apiResponseSummary != null && "success".equals(apiResponseSummary.getStatus())) {
                                            SummaryData summaryData = apiResponseSummary.getData();
                                            if (summaryData != null) {
                                                // Tampilkan data makanan dan summary ke UI
                                                displayMakananAndSummaryDetails(makanan, summaryData);
                                            } else {
                                                // Handle jika data summary tidak ditemukan
                                                Toast.makeText(DataMakanan.this, "Data Summary tidak ditemukan", Toast.LENGTH_SHORT).show();
                                                finish(); // Selesaikan activity jika data summary tidak ditemukan
                                            }
                                        } else {
                                            // Handle jika respons summary tidak sukses
                                            handleUnsuccessfulResponse(response);
                                        }
                                    } else {
                                        // Handle jika terjadi kesalahan saat mengambil data summary
                                        handleErrorResponse(response);
                                    }
                                }

                                @Override
                                public void onFailure(Call<ApiResponse<SummaryData>> call, Throwable t) {
                                    // Handle jika terjadi kesalahan jaringan saat mengambil data summary
                                    handleNetworkError(t);
                                }
                            });
                        } else {
                            // Handle jika data makanan tidak ditemukan
                            Toast.makeText(DataMakanan.this, "Data Makanan tidak ditemukan", Toast.LENGTH_SHORT).show();
                            finish(); // Selesaikan activity jika data makanan tidak ditemukan
                        }
                    } else {
                        // Handle jika respons makanan tidak sukses
                        handleUnsuccessfulResponse(response);
                    }
                } else {
                    // Handle jika terjadi kesalahan saat mengambil data makanan
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<MakananModel>> call, Throwable t) {
                // Handle jika terjadi kesalahan jaringan saat mengambil data makanan
                handleNetworkError(t);
            }
        });
    }

    // Metode untuk menampilkan data makanan dan summary ke UI
    private void displayMakananAndSummaryDetails(MakananModel makanan, SummaryData summaryData) {
        // Setel data makanan ke elemen UI
        namaMakananTextView.setText(makanan.getNama());

        DecimalFormat decimalFormat = new DecimalFormat("#.#");

        // Setel nilai ke TextView dengan memanipulasi teks
        karbohidratTextView.setText(decimalFormat.format(makanan.getKarbohidrat()));
        proteinTextView.setText(decimalFormat.format(makanan.getProtein()));
        gulaTextView.setText(decimalFormat.format(makanan.getGula()));
        lemakTextView.setText(decimalFormat.format(makanan.getLemak()));
        garamTextView.setText(decimalFormat.format(makanan.getGaram()));


        // batas kandungan get
        batasKarbohidrat.setText(decimalFormat.format(summaryData.getBatas_karbohidrat()));
        batasProtein.setText(decimalFormat.format(summaryData.getBatas_protein()));
        batasGula.setText(decimalFormat.format(summaryData.getBatas_gula()));
        batasLemak.setText(decimalFormat.format(summaryData.getBatas_lemak()));
        batasGaram.setText(decimalFormat.format(summaryData.getBatas_garam()));

        // Hitung persentase untuk setiap jenis nutrisi berdasarkan batas yang didapatkan dari summary
        double batasKarbo = summaryData.getBatas_karbohidrat();
        double batasProtein = summaryData.getBatas_protein();
        double batasGula = summaryData.getBatas_gula();
        double batasLemak = summaryData.getBatas_lemak();
        double batasGaram = summaryData.getBatas_garam();


        int hasilKarboPersen = (int) ((double) makanan.getKarbohidrat() / batasKarbo * 100);
        int hasilProteinPersen = (int) ((double) makanan.getProtein() / batasProtein * 100);
        int hasilGulaPersen = (int) ((double) makanan.getGula() / batasGula * 100);
        int hasilLemakPersen = (int) ((double) makanan.getLemak() / batasLemak * 100);
        int hasilGaramPersen = (int) ((double) makanan.getGaram() / batasGaram * 100);

        // Setel persentase ke ProgressBar atau elemen UI lainnya
        // Misalnya, jika Anda memiliki ProgressBar dengan ID progressBarKarbohidrat, maka:
        ProgressBar progressBarKarbohidrat = findViewById(R.id.bar_carbo);
        ProgressBar progressBarProtein = findViewById(R.id.bar_protein);
        ProgressBar progressBarGula = findViewById(R.id.bar_gula);
        ProgressBar progressBarLemak = findViewById(R.id.bar_lemak);
        ProgressBar progressBarGaram = findViewById(R.id.bar_garam);

        // Baca hasil ProgressBar
        progressBarKarbohidrat.setProgress(hasilKarboPersen);
        progressBarProtein.setProgress(hasilProteinPersen);
        progressBarGula.setProgress(hasilGulaPersen);
        progressBarGaram.setProgress(hasilGaramPersen);
        progressBarLemak.setProgress(hasilLemakPersen);

        // Memanggil id hasil persen
        TextView hpKarbohidrat = findViewById(R.id.hasil_carbo_persen);
        TextView hpProtein = findViewById(R.id.hasil_protein_persen);
        TextView hpGula = findViewById(R.id.hasil_gula_persen);
        TextView hpLemak = findViewById(R.id.hasil_lemak_persen);
        TextView hpGaram = findViewById(R.id.hasil_garam_persen);

        hpKarbohidrat.setText(String.valueOf(hasilKarboPersen));
        hpProtein.setText(String.valueOf(hasilProteinPersen));
        hpGula.setText(String.valueOf(hasilGulaPersen));
        hpLemak.setText(String.valueOf(hasilLemakPersen));
        hpGaram.setText(String.valueOf(hasilGaramPersen));

        // Load gambar menggunakan Glide
        Glide.with(this)
                .load(makanan.getGambar())
                .into(gambarMakananImageView);
    }

    // Metode untuk menangani respons tidak sukses
    private void handleUnsuccessfulResponse(Response<?> response) {
        String responseString = new Gson().toJson(response.body());
        Log.e("DataMakanan", "Unsuccessful API response: " + responseString);
        Log.e("DataMakanan", "Raw Response: " + response.raw().toString());
        Log.e("DataMakanan", "Headers: " + response.headers().toString());
        Log.e("DataMakanan", "Body: " + response.body().toString());
        Toast.makeText(DataMakanan.this, "Respons tidak sukses", Toast.LENGTH_SHORT).show();
        finish();
    }

    // Metode untuk menangani kesalahan jaringan
    private void handleNetworkError(Throwable t) {
        Toast.makeText(DataMakanan.this, "Terjadi kesalahan jaringan", Toast.LENGTH_SHORT).show();
        Log.e("DataMakanan", "Kesalahan jaringan", t);
        finish(); // Selesaikan activity jika terjadi kesalahan jaringan
    }

    // Metode untuk menangani kesalahan saat mengambil data dari API
    private void handleErrorResponse(Response<?> response) {
        Log.e("DataMakanan", "Error getting data from API. HTTP Response Code: " + response.code());
        Toast.makeText(DataMakanan.this, "Gagal mengambil data makanan", Toast.LENGTH_SHORT).show();
        finish();
    }

    private String getAuthToken() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("auth_token", MODE_PRIVATE);
        String authToken = sharedPreferences.getString("token", "");
        Log.d("AuthToken", "Token: " + authToken); // Tambahkan log ini
        return authToken;
    }

    public void kembaliKeMakanan(View view) {
        finish();
    }
}
