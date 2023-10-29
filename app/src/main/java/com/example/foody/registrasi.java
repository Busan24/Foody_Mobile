package com.example.foody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.app.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class registrasi extends AppCompatActivity {

    private EditText dateEditText;
    private Calendar calendar;
    private Spinner genderSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        TextView loginTextView = findViewById(R.id.text_login);

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(registrasi.this, MainActivity.class);
                startActivity(intent);
            }
        });

        TextView privacyPoliceTextView = findViewById(R.id.privacy_police);

        privacyPoliceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(registrasi.this, fitur_catatanku.class);
                startActivity(intent);
            }
        });

        TextView serviceTextView = findViewById(R.id.tm_service);

        serviceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(registrasi.this, FiturProfil.class);
                startActivity(intent);
            }
        });

        dateEditText = findViewById(R.id.date_regis);

        calendar = Calendar.getInstance();

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(registrasi.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                                dateEditText.setText(selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay);
                            }
                        }, year, month, day);

                datePickerDialog.show();
            }
        });


        Spinner spinner = findViewById(R.id.spinner_main);
        ArrayList<String> arrayList = new ArrayList<>();

// Menambahkan item "Jenis Kelamin" ke Spinner (dalam mode hint)
        arrayList.add("Jenis Kelamin");
        arrayList.add("Laki-Laki");
        arrayList.add("Perempuan");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_hint_item, arrayList){
            @Override
            public boolean isEnabled(int position) {
                // Menonaktifkan item "Jenis Kelamin"
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;

                if (position == 0) {
                    // Jika ini adalah item "Jenis Kelamin", atur teks warna hint
                    textView.setTextColor(ContextCompat.getColor(getContext(), android.R.color.darker_gray));
                } else {
                    // Jika bukan item "Jenis Kelamin", atur teks warna normal
                    textView.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
                }

                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String item = adapterView.getItemAtPosition(position).toString();

                if (position != 0) {
                    Toast.makeText(registrasi.this, "Jenis Kelamin: " + item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


}

