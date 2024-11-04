package com.orion.foody;

// BmiHistoryAdapter.java

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BmiHistoryAdapter extends RecyclerView.Adapter<BmiHistoryAdapter.ViewHolder> {

    private List<HistoryBmiModel> historyList;
    private Context context;

    public BmiHistoryAdapter(List<HistoryBmiModel> historyList, Context context) {
        this.historyList = historyList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_data_bmi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryBmiModel historyItem = historyList.get(position);

        // Set tanggal dan hari
        holder.tanggalTextView.setText(historyItem.getTanggal());
        holder.hariTextView.setText(historyItem.getHari());


        // Set data per hari menggunakan adapter baru
        BmiPerDayAdapter perDayAdapter = new BmiPerDayAdapter(historyItem.getData());
        holder.dataPerHariRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.dataPerHariRecyclerView.setAdapter(perDayAdapter);
    }


    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tanggalTextView;
        public TextView hariTextView;
        public RecyclerView dataPerHariRecyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tanggalTextView = itemView.findViewById(R.id.tgl_wkt_rc);
            hariTextView = itemView.findViewById(R.id.hari_rc);

            dataPerHariRecyclerView = itemView.findViewById(R.id.data_per_hari_recycler_view);
        }
    }
}



