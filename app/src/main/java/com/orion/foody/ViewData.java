package com.orion.foody;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewData extends AppCompatActivity {
    ListView listView;
    DBDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        listView = findViewById(R.id.lv_daftar_makanan);
        dataSource = new DBDataSource(this);
        dataSource.open();

        ArrayList<Catatanku> catatankuList = dataSource.getAllCatatanku();
        CatatankuAdapter adapter = new CatatankuAdapter(this, catatankuList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Catatanku catatanku = catatankuList.get(position);
                // Buka ViewSingleData dan teruskan data yang sesuai
                Intent intent = new Intent(ViewData.this, ViewSingleData.class);
                intent.putExtra("nama_makanan", catatanku.getMakanan());
                intent.putExtra("waktu", catatanku.getWaktu());
                intent.putExtra("jumlah", catatanku.getJumlah());
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Catatanku catatanku = catatankuList.get(position);
                showCustomDialog(catatanku);
                return true;
            }
        });

    }


    private void showCustomDialog(Catatanku catatanku) {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnEdit = dialog.findViewById(R.id.btn_edit);
        Button btnDelete = dialog.findViewById(R.id.btn_delete);


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tutup dialog
                dialog.dismiss();

                Intent intent = new Intent(ViewData.this, EditData.class);
                intent.putExtra("id", catatanku.getId());
                intent.putExtra("nama_makanan", catatanku.getMakanan());
                intent.putExtra("waktu", catatanku.getWaktu());
                intent.putExtra("jumlah", catatanku.getJumlah());
                startActivity(intent);
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSource.deleteCatatanku(catatanku.getId());
                refreshDaftar();
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void refreshDaftar() {
        ArrayList<Catatanku> catatankuList = dataSource.getAllCatatanku();
        CatatankuAdapter adapter = new CatatankuAdapter(this, catatankuList);
        listView.setAdapter(adapter);
    }

    public void aksi_back_2_menu(View view) {
        Intent intent = new Intent(this, fitur_catatanku.class);
        startActivity(intent);
    }
}
