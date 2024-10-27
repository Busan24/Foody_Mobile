package com.example.foody;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.Toast;
import android.widget.Button;

import android.util.Log;

import android.content.Intent;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarTransaksiActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private List<TransactionData> transactionList = new ArrayList<>();
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_daftar_transaksi);

        buttonBack = findViewById(R.id.btn_close);

        buttonBack.setOnClickListener(view -> {
            finish();
        });

        recyclerView = findViewById(R.id.transactionrv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransactionAdapter(transactionList);
        recyclerView.setAdapter(adapter);

        fetchTransactions();
    }

    private void fetchTransactions() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<ApiResponse<List<TransactionData>>> call = apiService.getTransactions("Bearer " + getAuthToken());

        call.enqueue(new Callback<ApiResponse<List<TransactionData>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<TransactionData>>> call, Response<ApiResponse<List<TransactionData>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    transactionList.clear();
                    transactionList.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<TransactionData>>> call, Throwable t) {
                Toast.makeText(DaftarTransaksiActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
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