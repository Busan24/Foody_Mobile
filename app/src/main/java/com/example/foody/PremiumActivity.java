package com.example.foody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class PremiumActivity extends AppCompatActivity {

    private Button prem1Bulan, prem3Bulan, prem6Bulan, berlangganan;
    private ScrollView scroll;
    private String idLanggananDipilih;
    List<LanggananModel> listLangganan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);

        // Inisialisasi button
        prem1Bulan = findViewById(R.id.prem_1bulan);
        prem3Bulan = findViewById(R.id.prem_3bulan);
        prem6Bulan = findViewById(R.id.prem_6bulan);
        berlangganan = findViewById(R.id.berlangganan);
        scroll = findViewById(R.id.scroll);

        getLanggananData();

        // Set tombol 1 bulan aktif di awal
        setActiveButton(prem1Bulan, "Berlangganan 1 Bulan", R.drawable.kotak_prem_pink);

        // Klik tombol 1 bulan
        prem1Bulan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveButton(prem1Bulan, "Berlangganan 1 Bulan", R.drawable.kotak_prem_pink);
                setInactiveButton(prem3Bulan, R.drawable.kotak_prem_abu);
                setInactiveButton(prem6Bulan, R.drawable.kotak_prem_abu);
                setIdLanggananDipilih(getListLangganan().get(0).getId());
                scroll.post(new Runnable() {
                    @Override
                    public void run() {
                        scroll.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        });

        // Klik tombol 3 bulan
        prem3Bulan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveButton(prem3Bulan, "Berlangganan 3 Bulan", R.drawable.kotak_prem_biru);
                setInactiveButton(prem1Bulan, R.drawable.kotak_prem_abu);
                setInactiveButton(prem6Bulan, R.drawable.kotak_prem_abu);
                setIdLanggananDipilih(getListLangganan().get(1).getId());
                scroll.post(new Runnable() {
                    @Override
                    public void run() {
                        scroll.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        });

        // Klik tombol 6 bulan
        prem6Bulan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveButton(prem6Bulan, "Berlangganan 6 Bulan", R.drawable.kotak_prem_pink);
                setInactiveButton(prem1Bulan, R.drawable.kotak_prem_abu);
                setInactiveButton(prem3Bulan, R.drawable.kotak_prem_abu);
                setIdLanggananDipilih(getListLangganan().get(2).getId());
                scroll.post(new Runnable() {
                    @Override
                    public void run() {
                        scroll.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        });

        berlangganan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               createTransaction(getIdLanggananDipilih());
            }
        });
    }

    public List<LanggananModel> getListLangganan() {
        return listLangganan;
    }

    public void setListLangganan(List<LanggananModel> listLangganan) {
        this.listLangganan = listLangganan;
    }

    public String getIdLanggananDipilih() {
        return idLanggananDipilih;
    }
    public void setIdLanggananDipilih(java.lang.String idLanggananDipilih) {
        this.idLanggananDipilih = idLanggananDipilih;
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
        finish();
    }



    private void getLanggananData() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        // Panggil API dengan Bearer Token
        Call<LanggananResponse> call = apiService.getLangganan("Bearer " + getAuthToken());
        call.enqueue(new Callback<LanggananResponse>() {
            @Override
            public void onResponse(Call<LanggananResponse> call, Response<LanggananResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Respons berhasil
                    List<LanggananModel> langgananList = response.body().getData();
                    // Lakukan sesuatu dengan langgananList
                    setListLangganan(langgananList);
                    setIdLanggananDipilih(langgananList.get(0).getId());
                } else {
                    // Tangani error dari server
                    Toast.makeText(PremiumActivity.this, "Gagal mendapatkan data langganan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LanggananResponse> call, Throwable t) {
                // Tangani error jaringan atau kesalahan lainnya
                Log.e("PremiumActivity", "Error: " + t.getMessage());
            }
        });
    }

    private void createTransaction(String langgananId) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        TransaksiResuestModel transaksiResuestModel = new TransaksiResuestModel(langgananId);

        Call <ApiResponse<TransaksiResponseModel>> call = apiService.createTransaction("Bearer " + getAuthToken(), transaksiResuestModel);
        call.enqueue(new Callback<ApiResponse<TransaksiResponseModel>>() {
            @Override
            public void onResponse(Call<ApiResponse<TransaksiResponseModel>> call, Response<ApiResponse<TransaksiResponseModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<TransaksiResponseModel> apiResponse = response.body();
                    TransaksiResponseModel transaksi = apiResponse.getData();

//                    Toast.makeText(PremiumActivity.this, "Transaksi Berhasil dibuat: " + transaksi.getSnap_token(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PremiumActivity.this, TransaksiActivity.class);
                    intent.putExtra("URL", "https://api-test.foody.web.id/api/v1/transaksi/bayar/" + transaksi.getId());
                    startActivity(intent);
                }
                else {
                    // Tangani error dari server
                    Toast.makeText(PremiumActivity.this, "Gagal membuat transaksi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<TransaksiResponseModel>> call, Throwable t) {
                // Tangani error jaringan atau kesalahan lainnya
                Log.e("PremiumActivity", "Error: " + t.getMessage());
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
