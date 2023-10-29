package com.example.foody;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class HistoryCatatnkuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_catatnku);

    }

    public void kembaliKeFiturCatatanku(View view) {
        Intent intent = new Intent(this, fitur_catatanku.class);
        startActivity(intent);
    }
}
