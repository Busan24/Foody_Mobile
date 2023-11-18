package com.example.foody;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

;
import java.util.ArrayList;
import java.util.List;

public class MakananAdapter extends RecyclerView.Adapter<MakananAdapter.ViewHolder> {
    private List<MakananModel> makananList;
    private OnItemClickListener mListener;
    public MakananAdapter(List<MakananModel> makananList) {
        this.makananList = makananList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public MakananModel getItem(int position) {
        return makananList.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_semua_makanan, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MakananModel makanan = makananList.get(position);

        // Set data ke tampilan item
        holder.namaMakananTextView.setText(makanan.getNama());

        // Load gambar menggunakan Glide
        Glide.with(holder.itemView.getContext())
                .load(makanan.getGambar().contains("upload/") ? "https://foody.azurewebsites.net/storage/" + makanan.getGambar() :
                        makanan.getGambar())
//                .placeholder(R.drawable.sweet_potato)
                .into(holder.gambarMakananImageView);

        // Tambahkan logika untuk menangani klik item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dapatkan ID makanan dan kirim ke Activity_data_makanan
//                String makananId = makanan.getId();
//                Intent intent = new Intent(v.getContext(), DataMakanan.class);
//                intent.putExtra("makanan", makananId);
//                v.getContext().startActivity(intent);
                if (mListener != null) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(position);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return makananList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView gambarMakananImageView;
        TextView namaMakananTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gambarMakananImageView = itemView.findViewById(R.id.semua_makanan);
            namaMakananTextView = itemView.findViewById(R.id.ns_makanan);
        }
    }

    public void filterList(List<MakananModel> filteredList) {
        makananList = filteredList;
        notifyDataSetChanged();
    }

    public void setFilter(List<MakananModel> filteredList) {
        makananList = new ArrayList<>();
        makananList.addAll(filteredList);
        notifyDataSetChanged();
    }

}


