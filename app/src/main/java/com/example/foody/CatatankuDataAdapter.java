package com.example.foody;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class CatatankuDataAdapter extends RecyclerView.Adapter<CatatankuDataAdapter.ViewHolder> {

    private List<CatatankuData> catatankuDataList;

    public void setData(List<CatatankuData> catatankuDataList) {
        this.catatankuDataList = catatankuDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate tata letak elemen Anda (activity_data_history.xml)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_data_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data ke ViewHolder Anda
        holder.bind(catatankuDataList.get(position));

        if (position % 2 == 0) {
            // Jika posisi genap, gunakan background pp_ot_pink
            holder.viewSeling.setBackgroundResource(R.drawable.pp_ot_pink);
        } else {
            // Jika posisi ganjil, gunakan background pp_ot_birucerah
            holder.viewSeling.setBackgroundResource(R.drawable.pp_ot_birucerah);
        }
    }

    @Override
    public int getItemCount() {
        // Return jumlah elemen dalam dataset
        return catatankuDataList != null ? catatankuDataList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNamaCatatan;
        private TextView tvJumlahMakanan;
        private TextView tvWaktuCatatan;
        private TextView tvKarbohidrat;
        private TextView tvProtein;
        private TextView tvGaram;
        private TextView tvGula;
        private TextView tvLemak;

        public View viewSeling;

        public ViewHolder(View itemView) {
            super(itemView);
            // Inisialisasi komponen ViewHolder Anda
            tvNamaCatatan = itemView.findViewById(R.id.nm_catatan);
            tvJumlahMakanan = itemView.findViewById(R.id.jt_makanan);
            tvWaktuCatatan = itemView.findViewById(R.id.waktu_catatanku);
            tvKarbohidrat = itemView.findViewById(R.id.catatanku_karbo);
            tvProtein = itemView.findViewById(R.id.catatanku_protein);
            tvGaram = itemView.findViewById(R.id.catatanku_garam);
            tvGula = itemView.findViewById(R.id.catatanku_gula);
            tvLemak = itemView.findViewById(R.id.catatanku_lemak);

            viewSeling = itemView.findViewById(R.id.view_seling);
        }

        public void bind(CatatankuData catatankuData) {

            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            // Set data ke tampilan
            tvNamaCatatan.setText(catatankuData.getNama_makanan());
            tvJumlahMakanan.setText(String.valueOf(catatankuData.getJumlah()));
            tvWaktuCatatan.setText(catatankuData.getWaktu());

            tvKarbohidrat.setText(decimalFormat.format(catatankuData.getKarbohidrat()));
            tvProtein.setText(decimalFormat.format(catatankuData.getProtein()));
            tvGaram.setText(decimalFormat.format(catatankuData.getGaram()));
            tvGula.setText(decimalFormat.format(catatankuData.getGula()));
            tvLemak.setText(decimalFormat.format(catatankuData.getLemak()));
        }
    }
}
