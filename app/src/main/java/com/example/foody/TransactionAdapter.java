package com.example.foody;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private List<TransactionData> transactionList;

    public TransactionAdapter(List<TransactionData> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_transaction_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransactionData transaction = transactionList.get(position);

        holder.packageName.setText("Premium " + transaction.getNama_paket());
        holder.paymentMethod.setText(transaction.getMetode_pembayaran().toUpperCase());
        holder.transactionNumber.setText(transaction.getNo_transaksi());
        holder.amount.setText(transaction.getJumlah_PembayaranRupiah());
        holder.date.setText(transaction.getTanggal());
        holder.time.setText(transaction.getJam());

        // Menentukan status dan icon
        switch (transaction.getStatus()) {
            case "settlement":
                holder.status.setText("sukses");
                holder.status.setTextColor(Color.parseColor("#5DC486")); // Sesuaikan warna
                holder.status.setBackgroundColor(Color.parseColor("#ccffea")); // Sesuaikan warna
                holder.statusIcon.setImageResource(R.drawable.icon_transaksi_success); // Icon sukses
                break;
            case "pending":
                holder.status.setText("pending");
                holder.status.setTextColor(Color.parseColor("#F49A47")); // Sesuaikan warna
                holder.status.setBackgroundColor(Color.parseColor("#fff1cc")); // Sesuaikan warna
                holder.statusIcon.setImageResource(R.drawable.icon_transaksi_pending); // Icon pending
                break;
            case "gagal":
                holder.status.setText("gagal");
                holder.status.setBackgroundColor(Color.RED); // Sesuaikan warna
                holder.statusIcon.setImageResource(R.drawable.icon_transaksi_cancel); // Icon gagal
                break;
        }
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView packageName, paymentMethod, transactionNumber, amount, status, date, time;
        ImageView statusIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            packageName = itemView.findViewById(R.id.nama_paket);
            paymentMethod = itemView.findViewById(R.id.metode_pembayaran);
            transactionNumber = itemView.findViewById(R.id.nomer_transaksi);
            amount = itemView.findViewById(R.id.total);
            status = itemView.findViewById(R.id.status);
            date = itemView.findViewById(R.id.tanggal);
            time = itemView.findViewById(R.id.waktu);
            statusIcon = itemView.findViewById(R.id.icon_status);
        }
    }

}
