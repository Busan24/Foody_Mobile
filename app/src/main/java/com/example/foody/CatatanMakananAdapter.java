package com.example.foody;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class CatatanMakananAdapter extends RecyclerView.Adapter<CatatanMakananAdapter.ViewHolder> {

    private List<CatatanMakananModel> catatanMakananList;

    public CatatanMakananAdapter(List<CatatanMakananModel> catatanMakananList) {
        this.catatanMakananList = catatanMakananList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_create_catatan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data ke tampilan
        CatatanMakananModel catatanMakanan = catatanMakananList.get(position);
        holder.bind(catatanMakanan);

        // Mengatur background selang-seling
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
        return catatanMakananList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNamaMakanan;
        private TextView txtWaktu;
        private TextView txtJumlahPorsi;

        public View viewSeling;

        private TextView catatankuKarbo, catatankuProterin, cacatankuGula, catatankuLemak, catatankuGaram, namaMakananDaily;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaMakananDaily = itemView.findViewById(R.id.nm_catatan);
            txtWaktu = itemView.findViewById(R.id.waktu_catatanku);
            txtJumlahPorsi = itemView.findViewById(R.id.jt_makanan);
            catatankuKarbo = itemView.findViewById(R.id.catatanku_karbo);
            catatankuProterin = itemView.findViewById(R.id.catatanku_protein);
            catatankuGaram = itemView.findViewById(R.id.catatanku_garam);
            catatankuLemak = itemView.findViewById(R.id.catatanku_lemak);
            cacatankuGula = itemView.findViewById(R.id.catatanku_gula);

            viewSeling = itemView.findViewById(R.id.view_seling);
        }

        public void bind(CatatanMakananModel catatanMakanan) {

            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            // Set data ke tampilan masing-masing
            namaMakananDaily.setText(catatanMakanan.getNama_makanan());
            txtWaktu.setText(catatanMakanan.getWaktu());
            txtJumlahPorsi.setText(String.valueOf(catatanMakanan.getJumlah()));

            catatankuKarbo.setText(decimalFormat.format(catatanMakanan.getKarbohidrat()));
            catatankuProterin.setText(decimalFormat.format(catatanMakanan.getProtein()));
            catatankuGaram.setText(decimalFormat.format(catatanMakanan.getGaram()));
            catatankuLemak.setText(decimalFormat.format(catatanMakanan.getLemak()));
            cacatankuGula.setText(decimalFormat.format(catatanMakanan.getGula()));
        }
    }
}

