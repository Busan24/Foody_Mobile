package com.example.foody;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

public class EditData extends AppCompatActivity {
    EditText editMakanan;
    EditText editWaktu;
    EditText editJumlah;
    long catatankuId;
    DBDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        editMakanan = findViewById(R.id.edit_makanan);
        editWaktu = findViewById(R.id.edit_waktu);
        editJumlah = findViewById(R.id.edit_jumlah);

        // Ambil data yang dikirim dari ViewData
        Intent intent = getIntent();
        if (intent != null) {
            catatankuId = intent.getLongExtra("id", -1);
            String namaMakanan = intent.getStringExtra("nama_makanan");
            String waktu = intent.getStringExtra("waktu");
            String jumlah = intent.getStringExtra("jumlah");

            // Tampilkan data pada EditText yang sesuai
            editMakanan.setText(namaMakanan);
            editWaktu.setText(waktu);
            editJumlah.setText(jumlah);
        }

        dataSource = new DBDataSource(this);
        dataSource.open();

        Button tmbSimpan = findViewById(R.id.tmb_simpan);
        tmbSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ambil data yang telah diubah
                String namaMakananBaru = editMakanan.getText().toString();
                String waktuBaru = editWaktu.getText().toString();
                String jumlahBaru = editJumlah.getText().toString();

                // Simpan data yang telah diubah ke dalam database
                Catatanku catatanku = new Catatanku();
                catatanku.setId(catatankuId);
                catatanku.setMakanan(namaMakananBaru);
                catatanku.setWaktu(waktuBaru);
                catatanku.setJumlah(jumlahBaru);

                dataSource.updateCatatanku(catatanku); // Update data ke dalam database

                // Setelah data disimpan, Anda dapat kembali ke ViewData
                Intent intent = new Intent(EditData.this, ViewData.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }
}
