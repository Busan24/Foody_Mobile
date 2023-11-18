package com.example.foody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_hint_item, arrayList) {
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

        Spinner aktivitasSpinner = findViewById(R.id.spinner_aktivitas);
        ArrayList<String> aktivitasList = new ArrayList<>();
        aktivitasList.add("Aktivitas");
        aktivitasList.add("Tidak aktif (tidak berolahraga sama sekali)");
        aktivitasList.add("Cukup aktif (berolahraga 1-3x seminggu)");
        aktivitasList.add("Aktif (berolahraga 3-5x seminggu)");
        aktivitasList.add("Sangat aktif (berolahraga atau 6-7x seminggu)");

        ArrayList<Integer> aktivitasValues = new ArrayList<>();
        aktivitasValues.add(0); // Placeholder untuk aktivitas
        aktivitasValues.add(1); // Tidak aktif
        aktivitasValues.add(2); // Cukup aktif
        aktivitasValues.add(3); // Aktif
        aktivitasValues.add(4); // Sangat aktif

        ArrayAdapter<String> aktivitasAdapter = new ArrayAdapter<String>(this, R.layout.spinner_hint_item, aktivitasList) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;

                if (position == 0) {
                    textView.setTextColor(ContextCompat.getColor(getContext(), android.R.color.darker_gray));
                } else {
                    textView.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
                }

                return view;
            }
        };

        aktivitasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aktivitasSpinner.setAdapter(aktivitasAdapter);

        aktivitasSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {
                    int selectedAktivitasValue = aktivitasValues.get(position);
                    Toast.makeText(registrasi.this, "Aktivitas: " + selectedAktivitasValue, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button registerButton = findViewById(R.id.btn_register);
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dapatkan masukan pengguna dari kolom EditText
                String name = ((EditText) findViewById(R.id.name_regis)).getText().toString();
                String email = ((EditText) findViewById(R.id.email_regis)).getText().toString();
                String username = ((EditText) findViewById(R.id.username_regis)).getText().toString();
                String password = ((EditText) findViewById(R.id.password_regis)).getText().toString();
                String jenisKelamin = spinner.getSelectedItem().toString(); // Ambil dari Spinner
                String tanggalLahir = dateEditText.getText().toString(); // Ambil dari DatePicker
                int aktivitas = aktivitasValues.get(aktivitasSpinner.getSelectedItemPosition());
                double tinggiBadan = 0.0;
                double beratBadan = 0.0;

                try {
                    tinggiBadan = Double.parseDouble(((EditText) findViewById(R.id.tb_regis)).getText().toString());
                    beratBadan = Double.parseDouble(((EditText) findViewById(R.id.bb_regis)).getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                // Validasi input
                if (name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || jenisKelamin.equals("Jenis Kelamin") || tanggalLahir.isEmpty() || aktivitas == 0 || tinggiBadan == 0.0 || beratBadan == 0.0) {
                    // Menampilkan peringatan jika ada input yang belum diisi
                    Toast.makeText(registrasi.this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    // Menampilkan peringatan jika alamat email tidak valid
                    Toast.makeText(registrasi.this, "Alamat email tidak valid", Toast.LENGTH_SHORT).show();
                    // Focus ke EditText email
                    ((EditText) findViewById(R.id.email_regis)).requestFocus();
                } else {
                    // Buat model permintaan
                    RegistrationRequestModel requestModel = new RegistrationRequestModel(name, email, username, password, jenisKelamin, tanggalLahir, aktivitas, tinggiBadan, beratBadan);

                    // Lakukan permintaan API
                    Call<RegistrationRequestModel> call = apiService.registerUser(requestModel);
                    call.enqueue(new Callback<RegistrationRequestModel>() {
                        @Override
                        public void onResponse(Call<RegistrationRequestModel> call, Response<RegistrationRequestModel> response) {
                            if (response.isSuccessful()) {
                                // Pendaftaran berhasil, tangani respons
                                RegistrationRequestModel responseBody = response.body();
                                // ... (tangani kesuksesan)
                                Toast.makeText(registrasi.this, "Pendaftaran berhasil!", Toast.LENGTH_SHORT).show();

                                // Handler untuk menunda sebelum mengalihkan ke halaman activity_main.xml
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Alihkan pengguna ke halaman activity_main.xml
                                        Intent intent = new Intent(registrasi.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, 50);
                            } else {
                                // Pendaftaran gagal, tangani kesalahan
                                // Anda dapat memeriksa kode respons dan body untuk detail lebih lanjut
                                // ...
                            }
                        }

                        @Override
                        public void onFailure(Call<RegistrationRequestModel> call, Throwable t) {
                            // Permintaan pendaftaran gagal
                            // Tangani kesalahan
                            // ...
                        }
                    });
                }
            }
        });

    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
