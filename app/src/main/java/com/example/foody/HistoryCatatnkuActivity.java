package com.example.foody;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryCatatnkuActivity extends AppCompatActivity {

    private RecyclerView rvHistory;
    private HistoryAdapter historyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_catatnku);

        rvHistory = findViewById(R.id.rvcatatanku_history);
        historyAdapter = new HistoryAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvHistory.setLayoutManager(layoutManager);
        rvHistory.setAdapter(historyAdapter);

        // Ambil dan tampilkan riwayat
        fetchAndDisplayHistory();

    }

    private void fetchAndDisplayHistory() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        String authToken = "Bearer " + getAuthToken();
        Call<ApiResponse<List<HistoryResponse>>> call = apiService.getHistory(authToken);
        call.enqueue(new Callback<ApiResponse<List<HistoryResponse>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<HistoryResponse>>> call, Response<ApiResponse<List<HistoryResponse>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<HistoryResponse> historyList = response.body().getData();
                    historyAdapter.setData(historyList);
                    historyAdapter.notifyDataSetChanged();
                } else {
                    Log.e("HistoryCatatnku", "Gagal mendapatkan riwayat. Kode: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<HistoryResponse>>> call, Throwable t) {
                Log.e("HistoryCatatnku", "Gagal melakukan panggilan API: " + t.getMessage());
            }
        });
    }

    private String getAuthToken() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("auth_token", MODE_PRIVATE);
        String authToken = sharedPreferences.getString("token", "");
        Log.d("AuthToken", "Token: " + authToken); // Tambahkan log ini
        return authToken;
    }


    public void kembaliKeFiturCatatanku(View view) {
        Intent intent = new Intent(this, fitur_catatanku.class);
        startActivity(intent);
    }
}
