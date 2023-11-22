package com.example.foody;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BmiHistory extends AppCompatActivity {

    private RecyclerView rvBmiHistory;
    private BmiHistoryAdapter bmiHistoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_history);

        // Inisialisasi RecyclerView
        rvBmiHistory = findViewById(R.id.rvbmi_history);

        bmiHistoryAdapter = new BmiHistoryAdapter(new ArrayList<>(), BmiHistory.this);
        rvBmiHistory.setAdapter(bmiHistoryAdapter);

        rvBmiHistory.setLayoutManager(new LinearLayoutManager(this));

        // Ambil data dari API dan set ke adapter
        getBmiHistory();
    }

    private void getBmiHistory() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        String authToken = "Bearer " + getAuthToken();
        Call<ApiResponse<List<HistoryBmiModel>>> call = apiService.getBmiHistory(authToken);

        call.enqueue(new Callback<ApiResponse<List<HistoryBmiModel>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<HistoryBmiModel>>> call, Response<ApiResponse<List<HistoryBmiModel>>> response) {
                if (response.isSuccessful()) {
                    List<HistoryBmiModel> historyList = response.body().getData();
                    bmiHistoryAdapter = new BmiHistoryAdapter(historyList, BmiHistory.this);
                    rvBmiHistory.setAdapter(bmiHistoryAdapter);
                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<HistoryBmiModel>>> call, Throwable t) {
                // Handle failure
            }
        });
    }


    private String getAuthToken() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("auth_token", MODE_PRIVATE);
        String authToken = sharedPreferences.getString("token", "");
        Log.d("AuthToken", "Token: " + authToken); // Tambahkan log ini
        return authToken;
    }

    public void kembaliKalkulatorBmi(View view) {
        Intent intent = new Intent(this, KalkulatorBmi.class);
        startActivity(intent);
    }
}