package com.orion.foody;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class CatatankuAdapter extends ArrayAdapter<Catatanku> {
    private ArrayList<Catatanku> catatankuList;

    public CatatankuAdapter(Context context, ArrayList<Catatanku> catatankuList) {
        super(context, 0, catatankuList);
        this.catatankuList = catatankuList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Catatanku catatanku = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);
        }

        TextView tvNamaMakanan = convertView.findViewById(R.id.tv_nama_makanan);
        TextView tvWaktu = convertView.findViewById(R.id.tv_waktu);
        TextView tvJumlah = convertView.findViewById(R.id.tv_jumlah);

        tvNamaMakanan.setText("Nama Makanan: " + catatanku.getMakanan());
        tvWaktu.setText("Waktu: " + catatanku.getWaktu());
        tvJumlah.setText("Jumlah: " + catatanku.getJumlah());

        return convertView;
    }
}

