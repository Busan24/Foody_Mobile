package com.example.foody;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RekomendasiProduk extends AppCompatActivity {

    private RecyclerView rvRekomendasi;
    private ProdukAdapter produkAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekomendasi_produk);

        // Inisialisasi RecyclerView
        rvRekomendasi = findViewById(R.id.rvrekom_produk);
        produkAdapter = new ProdukAdapter();
        rvRekomendasi.setLayoutManager(new GridLayoutManager(this, 2));
        rvRekomendasi.setAdapter(produkAdapter);

        searchView = findViewById(R.id.search_produk);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Saat teks pencarian berubah, terapkan filter ke adapter
                produkAdapter.getFilter().filter(newText);
                return true;
            }
        });

        // Panggil metode untuk mendapatkan data produk
        fetchDataProduk();


    }


    private void fetchDataProduk() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<ApiResponse<List<Produk>>> call = apiService.getProduk();

        call.enqueue(new Callback<ApiResponse<List<Produk>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Produk>>> call, Response<ApiResponse<List<Produk>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Produk> produkList = response.body().getData();
                    produkAdapter.setData(produkList);
                    produkAdapter.notifyDataSetChanged();
                } else {
                    // Handle error
                    Log.e("RekomendasiProduk", "Gagal mendapatkan produk. Kode: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Produk>>> call, Throwable t) {
                // Handle failure
                Log.e("RekomendasiProduk", "Gagal melakukan panggilan API: " + t.getMessage());
            }
        });
    }

    public void kembaliKeHome(View view) {
        Intent intent = new Intent(this, HomeFoodyActivity.class);
        startActivity(intent);
    }
}
