package com.orion.foody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class registrasi extends AppCompatActivity {

    private EditText dateEditText;
    private Calendar calendar;
    private Spinner genderSpinner;

    private ProgressDialog progressDialog;
    private ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        TextView loginTextView = findViewById(R.id.text_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Meregistrasi pengguna...");
        progressDialog.setCancelable(false);

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(registrasi.this, MainActivity.class);
                startActivity(intent);
            }
        });

//        TextView privacyPoliceTextView = findViewById(R.id.privacy_police);
//
//        privacyPoliceTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(registrasi.this, fitur_catatanku.class);
//                startActivity(intent);
//            }
//        });

//        TextView serviceTextView = findViewById(R.id.tm_service);
//
//        serviceTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(registrasi.this, FiturProfil.class);
//                startActivity(intent);
//            }
//        });

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
        aktivitasList.add("Super Aktif (berolahraga 1-2x setiap hari)");

        ArrayList<Double> aktivitasValues = new ArrayList<>();
        aktivitasValues.add(0.0); // Placeholder untuk aktivitas
        aktivitasValues.add(1.200); // Tidak aktif
        aktivitasValues.add(1.375); // Cukup aktif
        aktivitasValues.add(1.550); // Aktif
        aktivitasValues.add(1.725); // Sangat aktif
        aktivitasValues.add(1.9); // super

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
                    Double selectedAktivitasValue = aktivitasValues.get(position);
//                    Toast.makeText(registrasi.this, "Aktivitas: " + selectedAktivitasValue, Toast.LENGTH_SHORT).show();
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

                EditText nameEditText = findViewById(R.id.name_regis);
                EditText emailEditText = findViewById(R.id.email_regis);
//                EditText usernameEditText = findViewById(R.id.username_regis);
                EditText passwordEditText = findViewById(R.id.password_regis);
                Spinner genderSpinner = findViewById(R.id.spinner_main);
                Spinner activitySpinner = findViewById(R.id.spinner_aktivitas);
                EditText heightEditText = findViewById(R.id.tb_regis);
                EditText weightEditText = findViewById(R.id.bb_regis);
                EditText dateEditText = findViewById(R.id.date_regis);
                EditText confirmPasswordEditText = findViewById(R.id.cfpw_regis);

                // Dapatkan masukan pengguna dari kolom EditText
                String name = ((EditText) findViewById(R.id.name_regis)).getText().toString();
                String email = ((EditText) findViewById(R.id.email_regis)).getText().toString();
//                String username = ((EditText) findViewById(R.id.username_regis)).getText().toString();
                String password = ((EditText) findViewById(R.id.password_regis)).getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                String jenisKelamin = spinner.getSelectedItem().toString(); // Ambil dari Spinner
                String tanggalLahir = dateEditText.getText().toString(); // Ambil dari DatePicker

                String gender = genderSpinner.getSelectedItem().toString();
                String activity = activitySpinner.getSelectedItem().toString();
                String dateOfBirth = dateEditText.getText().toString();

                double aktivitas = aktivitasValues.get(aktivitasSpinner.getSelectedItemPosition());
                double tinggiBadan = 0.0;
                double beratBadan = 0.0;

                try {
                    tinggiBadan = Double.parseDouble(((EditText) findViewById(R.id.tb_regis)).getText().toString());
                    beratBadan = Double.parseDouble(((EditText) findViewById(R.id.bb_regis)).getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                if (name.isEmpty()) {
                    nameEditText.setError("Harap isi nama");
                    Toast.makeText(registrasi.this, "Nama belum diisi", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    emailEditText.setError("Harap isi email");
                    Toast.makeText(registrasi.this, "Email belum diisi", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    emailEditText.setError("Alamat email tidak valid");
                    Toast.makeText(registrasi.this, "Alamat email tidak valid", Toast.LENGTH_SHORT).show();
                }
//                else if (username.isEmpty()) {
//                    usernameEditText.setError("Harap isi username");
//                    Toast.makeText(registrasi.this, "Username belum diisi", Toast.LENGTH_SHORT).show();
//                }
                else if (password.isEmpty()) {
                    passwordEditText.setError("Harap isi password");
                    Toast.makeText(registrasi.this, "Password belum diisi", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 8) {
                    passwordEditText.setError("Password harus memiliki panjang minimal 8 karakter");
                    Toast.makeText(registrasi.this, "Password harus memiliki panjang minimal 8 karakter", Toast.LENGTH_SHORT).show();
                }else if (confirmPassword.isEmpty()) {
                    confirmPasswordEditText.setError("Harap konfirmasi password");
                    Toast.makeText(registrasi.this, "Konfirmasi password belum diisi", Toast.LENGTH_SHORT).show();
                } else if (!confirmPassword.equals(password)) {
                    confirmPasswordEditText.setError("Password tidak cocok");
                    Toast.makeText(registrasi.this, "Konfirmasi password tidak cocok", Toast.LENGTH_SHORT).show();
                    // Focus ke EditText confirmPassword
                    confirmPasswordEditText.requestFocus();
                } else if (gender.equals("Jenis Kelamin")) {
                    ((TextView) genderSpinner.getSelectedView()).setError("Pilih jenis kelamin");
                    Toast.makeText(registrasi.this, "Jenis kelamin belum dipilih", Toast.LENGTH_SHORT).show();
                } else if (activity.equals("Aktivitas")) {
                    ((TextView) activitySpinner.getSelectedView()).setError("Pilih tingkat aktivitas");
                    Toast.makeText(registrasi.this, "Tingkat aktivitas belum dipilih", Toast.LENGTH_SHORT).show();
                } else if (tinggiBadan == 0.0) {
                    heightEditText.setError("Harap isi tinggi badan");
                    Toast.makeText(registrasi.this, "Tinggi badan belum diisi", Toast.LENGTH_SHORT).show();
                } else if (beratBadan == 0.0) {
                    weightEditText.setError("Harap isi berat badan");
                    Toast.makeText(registrasi.this, "Berat badan belum diisi", Toast.LENGTH_SHORT).show();
                } else if (dateOfBirth.isEmpty()) {
                    dateEditText.setError("Harap isi tanggal lahir");
                    Toast.makeText(registrasi.this, "Tanggal lahir belum diisi", Toast.LENGTH_SHORT).show();
                } else {
                    // Semua validasi berhasil, lanjutkan dengan pendaftaran
                    progressDialog.show();
                    RegistrationRequestModel requestModel = new RegistrationRequestModel(name, email, password, jenisKelamin, tanggalLahir, aktivitas, tinggiBadan, beratBadan);
                    Call<RegistrationRequestModel> call = apiService.registerUser(requestModel);
                    call.enqueue(new Callback<RegistrationRequestModel>() {
                        @Override
                        public void onResponse(Call<RegistrationRequestModel> call, Response<RegistrationRequestModel> response) {
                            if (response.isSuccessful()) {
                                progressDialog.dismiss();
//                                OtpRequest otpRequest = new OtpRequest(email);
//                                Call<OtpResponse> otpCall = apiService.sendOtp(otpRequest);
                                // Pendaftaran berhasil, tangani respons
                                RegistrationRequestModel responseBody = response.body();
                                // ... (tangani kesuksesan)
                                Toast.makeText(registrasi.this, "Pendaftaran berhasil!", Toast.LENGTH_SHORT).show();

                                // Handler untuk menunda sebelum mengalihkan ke halaman activity_main.xml
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        // Alihkan pengguna ke halaman activity_main.xml
//                                        Intent intent = new Intent(registrasi.this, VerifikasiOtp.class);
//                                        startActivity(intent);
//                                        finish();
//                                    }
//                                }, 50);

                                loginAfterRegistration(email, password);
                            }  else {
                                progressDialog.dismiss();
//                                Toast.makeText(registrasi.this, "Pendaftaran Gagal.Coba Lagi", Toast.LENGTH_SHORT).show();
                                try {
                                    // Mengonversi respons error ke ApiResponse
                                    ApiError<?> errorResponse = new Gson().fromJson(response.errorBody().string(), ApiError.class);

                                    if (errorResponse != null) {
                                        if (errorResponse.getError() != null && errorResponse.getError() instanceof Map) {
                                            // Menampilkan pesan kesalahan spesifik untuk email dan username
                                            Map<String, List<String>> errorMap = (Map<String, List<String>>) errorResponse.getError();
                                            if (errorMap.containsKey("email")) {
                                                String emailError = errorMap.get("email").get(0);
                                                Toast.makeText(registrasi.this, "Email sudah terpakai", Toast.LENGTH_SHORT).show();
                                            }
//                                            if (errorMap.containsKey("username")) {
//                                                String usernameError = errorMap.get("username").get(0);
//                                                Toast.makeText(registrasi.this, "Username sudah terpakai",  Toast.LENGTH_SHORT).show();
//                                            }
                                        } else {
                                            // Menampilkan pesan kesalahan umum
                                            Toast.makeText(registrasi.this, "Pendaftaran gagal. Coba lagi nanti.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<RegistrationRequestModel> call, Throwable t) {
                            Toast.makeText(registrasi.this, "Gagal Registrasi. Periksa koneksi internet Anda.", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Setelah pemanggilan enqueue untuk registrasi


                }
            }
        });

    }

    private void loginAfterRegistration(String email, String password) {
        // Buat objek LoginRequestModel sesuai dengan kebutuhan Anda
        LoginRequestModel loginRequest = new LoginRequestModel(email, password);

        // Initialize apiService if it's not already initialized
        if (apiService == null) {
            apiService = RetrofitClient.getClient().create(ApiService.class);
        }

        // Check again if apiService is not null before making the API call
        if (apiService != null) {
            Call<LoginResponseModel> loginCall = apiService.loginUser(loginRequest);
            loginCall.enqueue(new Callback<LoginResponseModel>() {
                @Override
                public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                    if (response.isSuccessful()) {
                        // Login berhasil, tangani respons
                        LoginResponseModel loginResponse = response.body();
                        // ... (tangani kesuksesan login)



                        // Contoh: Tampilkan token untuk tujuan debugging
                        String token = loginResponse.getToken();
                        Log.d("Login Success", "Token: " + token);

                        // Misalnya, Anda dapat menyimpan token atau ID pengguna di sini
                        // ...
                        saveAuthToken(token);
                        saveLoginStatus(true);
                        saveVerifiedStatus(true);

                        // Lanjutkan dengan perpindahan ke halaman berikutnya setelah login berhasil
                        Intent intent = new Intent(registrasi.this, HomeFoodyActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Login gagal, tangani kesalahan
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("Login Failure", "Error Body: " + errorBody);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(registrasi.this, "Gagal Login. Cek kembali email dan password Anda.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                    Log.e("Login Failure", "Throwable: " + t.getMessage());
                    Toast.makeText(registrasi.this, "Gagal Login. Periksa koneksi internet Anda.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Handle the case where apiService is null
            Log.e("Login Failure", "API Service is not initialized");
            Toast.makeText(registrasi.this, "API Service is not initialized", Toast.LENGTH_SHORT).show();
        }
    }


    private void saveAuthToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("auth_token", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }

    private void saveLoginStatus(boolean status) {
        SharedPreferences sharedPreferences = getSharedPreferences("login_status", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_logged_in", status);
        editor.apply();
    }

    private void saveVerifiedStatus(boolean verified) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("verified_status", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_verified", verified);
        editor.apply();
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
