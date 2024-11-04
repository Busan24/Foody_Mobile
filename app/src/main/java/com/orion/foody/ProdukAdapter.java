package com.orion.foody;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ProdukViewHolder> implements Filterable {

    private List<Produk> produkList;

    private List<Produk> filteredProdukList;

    // Konstruktor dan metode setter untuk data

    @NonNull
    @Override
    public ProdukViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_data_produk, parent, false);
        return new ProdukViewHolder(view);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String filterQuery = charSequence.toString().toLowerCase().trim();
                filteredProdukList = new ArrayList<>();

                if (filterQuery.isEmpty()) {
                    filteredProdukList.addAll(produkList);
                } else {
                    for (Produk produk : produkList) {
                        if (produk.getNama().toLowerCase().contains(filterQuery)) {
                            filteredProdukList.add(produk);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredProdukList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredProdukList = (List<Produk>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukViewHolder holder, int position) {
        Produk produk = filteredProdukList.get(position);

        // Set data ke tampilan di sini
        holder.namaProduk.setText(produk.getNama());

        String formattedHarga = String.format("%,.0f", (double) produk.getHarga());
        holder.hargaProduk.setText(formattedHarga);

        holder.deskripsiProduk.setText(produk.getDeskripsi());
        Glide.with(holder.itemView.getContext())
                .load(produk.getGambar().contains("upload/") ? "https://foody.azurewebsites.net/storage/" + produk.getGambar() :
                        produk.getGambar())
//                .placeholder(R.drawable.sweet_potato)
                .into(holder.gambarProduk);
        // ... set data lainnya

        holder.linkProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aksi yang akan diambil ketika tombol diklik
                String link = produk.getLink();


                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                view.getContext().startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredProdukList != null ? filteredProdukList.size() : 0;
    }

    static class ProdukViewHolder extends RecyclerView.ViewHolder {
        ImageView gambarProduk;
        TextView namaProduk;
        TextView hargaProduk;
        TextView deskripsiProduk;
        Button linkProduk;
        ProdukViewHolder(@NonNull View itemView) {
            super(itemView);
            gambarProduk = itemView.findViewById(R.id.gambar_produk);
            namaProduk = itemView.findViewById(R.id.nama_produk);
            deskripsiProduk = itemView.findViewById(R.id.deskripsi_produk);
            hargaProduk = itemView.findViewById(R.id.harga_produk);
            linkProduk = itemView.findViewById(R.id.link_produk);

        }
    }

    // Metode setter untuk data
    public void setData(List<Produk> produkList) {
        this.produkList = produkList;
        this.filteredProdukList = new ArrayList<>(produkList); // Inisialisasi filteredProdukList
    }
}

