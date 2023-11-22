package com.example.foody;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BmiRecentAdapter extends RecyclerView.Adapter<BmiRecentAdapter.ViewHolder> {

    private List<BmiHistoryModel> bmiList;
    private OnDeleteClickListener onDeleteClickListener;



    public void removeBmiItem(int position) {
        if (bmiList != null && position >= 0 && position < bmiList.size()) {
            bmiList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position, BmiHistoryModel bmi);
    }


    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.onDeleteClickListener = listener;
    }


    // Constructor
    public BmiRecentAdapter() {
        this.bmiList = new ArrayList<>();
    }

    // Setter method to update the adapter's data
    public void setBmiList(List<BmiHistoryModel> bmiList) {
        this.bmiList = bmiList;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }



    // Create ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBmiValue;
        TextView tvBmiCategory;
        TextView tvBmiDate;

        Button ketBmi;

        public ViewHolder(View itemView) {
            super(itemView);
            // Initialize views from the item layout
            tvBmiValue = itemView.findViewById(R.id.hasil_bmi_data);
//            tvBmiCategory = itemView.findViewById(R.id.tv_bmi_category);
//            tvBmiDate = itemView.findViewById(R.id.tv_bmi_date);
                ketBmi = itemView.findViewById(R.id.ket_bmi);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_data_bmi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data to views
        BmiHistoryModel bmi = bmiList.get(position);
        holder.tvBmiValue.setText(String.valueOf(bmi.getNilai_bmi()));

        updateUiBasedOnBmiRange(holder, bmi.getNilai_bmi());
        holder.ketBmi.setText(String.valueOf(bmi.getKategori().getStatus()));
        holder.ketBmi.setTextColor(Color.parseColor(bmi.getKategori().getStrongColor()));

        holder.itemView.findViewById(R.id.delete_bmi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && onDeleteClickListener != null) {
                    onDeleteClickListener.onDeleteClick(adapterPosition, bmi);
                }
            }
        });
    }


    private void updateUiBasedOnBmiRange(ViewHolder holder, double bmiValue) {
        // Update UI based on BMI range
        View viewSeling = holder.itemView.findViewById(R.id.view_seling);
        Button ketBmi = holder.itemView.findViewById(R.id.ket_bmi); // Gunakan holder.itemView.findViewById

        if (bmiValue < 18.5) {
            viewSeling.setBackgroundResource(R.drawable.bb_kurang);
            ketBmi.setBackgroundResource(R.drawable.btn_kurangbb);
            ketBmi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.titik_biru, 0, 0, 0);
            ketBmi.setText(getStatusAndSetText("status", "strongColor"));
//            ketBmi.setTextColor(parseColorSafely(getStatusAndSetText("strongColor", "")));
        } else if (bmiValue >= 18.5 && bmiValue <= 24.9) {
            viewSeling.setBackgroundResource(R.drawable.bb_normal);
            ketBmi.setBackgroundResource(R.drawable.btn_normalbb);
            ketBmi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.titik_hijau, 0, 0, 0);
            ketBmi.setText(getStatusAndSetText("status", "strongColor"));
//            ketBmi.setTextColor(parseColorSafely(getStatusAndSetText("strongColor", "")));
        } else if (bmiValue >= 25 && bmiValue <= 29.9) {
            viewSeling.setBackgroundResource(R.drawable.bb_kelebihan);
            ketBmi.setBackgroundResource(R.drawable.btn_kelebihanbb);
            ketBmi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.titik_orange, 0, 0, 0);
            ketBmi.setText(getStatusAndSetText("status", "strongColor"));
//            ketBmi.setTextColor(parseColorSafely(getStatusAndSetText("strongColor", "")));
        } else if (bmiValue >= 30 && bmiValue <= 39.9) {
            viewSeling.setBackgroundResource(R.drawable.bb_obesitas);
            ketBmi.setBackgroundResource(R.drawable.button_merahcerah);
            ketBmi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.titik_merah, 0, 0, 0);
            ketBmi.setText(getStatusAndSetText("status", "strongColor"));
//            ketBmi.setTextColor(parseColorSafely(getStatusAndSetText("strongColor", "")));
        } else {
            viewSeling.setBackgroundResource(R.drawable.bb_obesitas);
            ketBmi.setBackgroundResource(R.drawable.button_merahcerah);
            ketBmi.setCompoundDrawablesWithIntrinsicBounds(R.drawable.titik_merah, 0, 0, 0);
            ketBmi.setText(getStatusAndSetText("status", "strongColor"));
//            ketBmi.setTextColor(parseColorSafely(getStatusAndSetText("strongColor", "")));
        }
    }


    private String getStatusAndSetText(String statusValue, String strongColorValue) {
        // Implement your logic to get the appropriate values based on the keys
        // For example, you might want to retrieve these values from your BmiHistoryModel
        // or use predefined values
        // Replace the placeholders below with your actual logic

        // Combine status and color values as needed
        return statusValue + " " + strongColorValue;
    }


    @Override
    public int getItemCount() {
        return bmiList.size();
    }

    private int parseColorSafely(String colorString) {
        try {
            return Color.parseColor(colorString);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            // Handle the error or return a default color
            return Color.BLACK;
        }
    }
}

