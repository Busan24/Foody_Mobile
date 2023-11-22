package com.example.foody;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<HistoryResponse> historyList;

    public void setData(List<HistoryResponse> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate tata letak elemen Anda (history_catatanku_perhari.xml)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_catatanku_perhari, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data ke ViewHolder Anda
        holder.bind(historyList.get(position));
    }

    @Override
    public int getItemCount() {
        // Return jumlah elemen dalam dataset
        return historyList != null ? historyList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvHari;
        private TextView tvTanggal;
        private RecyclerView rvCatatankuData;

        public ViewHolder(View itemView) {
            super(itemView);
            // Inisialisasi komponen ViewHolder Anda
            tvHari = itemView.findViewById(R.id.hari_rc);
            tvTanggal = itemView.findViewById(R.id.tgl_wkt_rc);
            rvCatatankuData = itemView.findViewById(R.id.data_history_catatanku);
        }

        public void bind(HistoryResponse historyResponse) {
            // Set data ke tampilan
            tvHari.setText(historyResponse.getHari());
            tvTanggal.setText(historyResponse.getTanggal());

            // Inisialisasi dan atur adapter untuk RecyclerView dalam RecyclerView
            CatatankuDataAdapter catatankuDataAdapter = new CatatankuDataAdapter();
            catatankuDataAdapter.setData(historyResponse.getData());
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext());
            rvCatatankuData.setLayoutManager(layoutManager);
            rvCatatankuData.setAdapter(catatankuDataAdapter);
        }
    }
}

