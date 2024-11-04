package com.orion.foody;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class BmiPerDayAdapter extends RecyclerView.Adapter<BmiPerDayAdapter.ViewHolder> {

    private List<BmiDataModel> dataList;

    public BmiPerDayAdapter(List<BmiDataModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bmi_history_perhari, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BmiDataModel dataItem = dataList.get(position);

        holder.nilaiBmiTextView.setText(String.valueOf(dataItem.getNilai_bmi()));

//        holder.ketBmi.setText(String.valueOf(dataItem.getKategori().getStatus()));
//        holder.ketBmi.setTextColor(Color.parseColor(dataItem.getKategori().getStrongColor()));

        double bmiValue = dataItem.getNilai_bmi();
        holder.nilaiBmiTextView.setText(String.valueOf(bmiValue));

        if (bmiValue < 18.5) {
            // Kategori: Berat badan kurang
            holder.viewSeling.setBackgroundResource(R.drawable.bb_kurang);
            holder.ketBmi.setBackgroundResource(R.drawable.btn_kurangbb);
            holder.ketBmi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.titik_biru, 0, 0, 0);
            holder.ketBmi.setText(dataItem.getKategori().getStatus());
            holder.ketBmi.setTextColor(Color.parseColor(dataItem.getKategori().getStrongColor()));
        } else if (bmiValue >= 18.5 && bmiValue <= 24.9) {
            // Kategori: Berat badan normal
            holder.viewSeling.setBackgroundResource(R.drawable.bb_normal);
            holder.ketBmi.setBackgroundResource(R.drawable.btn_normalbb);
            holder.ketBmi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.titik_hijau, 0, 0, 0);
            holder.ketBmi.setText(dataItem.getKategori().getStatus());
            holder.ketBmi.setTextColor(Color.parseColor(dataItem.getKategori().getStrongColor()));
        } else if (bmiValue >= 25 && bmiValue <= 29.9) {
            // Kategori: Kelebihan berat badan
            holder.viewSeling.setBackgroundResource(R.drawable.bb_kelebihan);
            holder.ketBmi.setBackgroundResource(R.drawable.btn_kelebihanbb);
            holder.ketBmi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.titik_orange, 0, 0, 0);
            holder.ketBmi.setText(dataItem.getKategori().getStatus());
            holder.ketBmi.setTextColor(Color.parseColor(dataItem.getKategori().getStrongColor()));
        } else if (bmiValue >= 30 && bmiValue <= 39.9) {
            // Kategori: Obesitas
            holder.viewSeling.setBackgroundResource(R.drawable.bb_obesitas);
            holder.ketBmi.setBackgroundResource(R.drawable.button_merahcerah);
            holder.ketBmi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.titik_merah, 0, 0, 0);
            holder.ketBmi.setText(dataItem.getKategori().getStatus());
            holder.ketBmi.setTextColor(Color.parseColor(dataItem.getKategori().getStrongColor()));
        } else {
            // Kategori: Obesitas tingkat III
            holder.viewSeling.setBackgroundResource(R.drawable.bb_obesitas);
            holder.ketBmi.setBackgroundResource(R.drawable.button_merahcerah);
            holder.ketBmi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.titik_merah, 0, 0, 0);
            holder.ketBmi.setText(dataItem.getKategori().getStatus());
            holder.ketBmi.setTextColor(Color.parseColor(dataItem.getKategori().getStrongColor()));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView idTextView;
        public TextView nilaiBmiTextView;
        public TextView waktuTextView;
        public TextView kategoriTextView;
        public View viewSeling;
        public Button ketBmi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nilaiBmiTextView = itemView.findViewById(R.id.hasil_bmi_data);

            viewSeling = itemView.findViewById(R.id.view_seling);
            ketBmi = itemView.findViewById(R.id.ket_bmi);
        }
    }
}

