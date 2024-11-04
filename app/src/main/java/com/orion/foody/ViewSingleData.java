package com.orion.foody;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewSingleData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_data);

        // Ambil data yang diteruskan dari ViewData
        Intent intent = getIntent();
        String namaMakanan = intent.getStringExtra("nama_makanan");
        String waktu = intent.getStringExtra("waktu");
        String jumlah = intent.getStringExtra("jumlah");

        // Setel data ke tampilan di ViewSingleData
        TextView tvNamaMakanan = findViewById(R.id.tv_nama_makanan);
        TextView tvWaktu = findViewById(R.id.tv_waktu);
        TextView tvJumlah = findViewById(R.id.tv_jumlah);

        tvNamaMakanan.setText(namaMakanan);
        tvWaktu.setText(waktu);
        tvJumlah.setText(jumlah);
    }

    // Metode untuk kembali ke ViewData
    public void aksiBacktToDaftar(View view) {
        Intent intent = new Intent(this, ViewData.class);
        startActivity(intent);
    }
}
