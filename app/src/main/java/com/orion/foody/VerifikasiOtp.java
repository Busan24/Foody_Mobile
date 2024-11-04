package com.orion.foody;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class VerifikasiOtp extends AppCompatActivity {

    private EditText verifOtpEmail1, verifOtpEmail2, verifOtpEmail3, verifOtpEmail4;
    private Button btnVerifikasi;
//    private ProgressBar otpProgressBar;

    private TextView kirimUlangOtp;
    private TextView logout;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 60000;

    private boolean isResendClicked = false;
    private TextView epEmail;
    private String authToken;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi_otp);

        // Inisialisasi elemen UI
        verifOtpEmail1 = findViewById(R.id.verif_otp_email1);
        verifOtpEmail2 = findViewById(R.id.verif_otp_email2);
        verifOtpEmail3 = findViewById(R.id.verif_otp_email3);
        verifOtpEmail4 = findViewById(R.id.verif_otp_email4);
        btnVerifikasi = findViewById(R.id.started);
//        otpProgressBar = findViewById(R.id.otpProgressBar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Checking Otp...");
        progressDialog.setCancelable(false);

        // Menangani klik tombol verifikasi

        kirimUlangOtp = findViewById(R.id.kirim_ulang_otp);
        logout = findViewById(R.id.logout);

        if (!isResendClicked) {
            // Jika belum, maka mulai countdown
            startCountdownTimer();
            updateCountdownText();
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser(getAuthToken());
            }
        });

        kirimUlangOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mengecek apakah timer sedang berjalan atau tidak
                if (countDownTimer == null) {
                    // Mulai countdown timer
                    startCountdownTimer();

                    // Kirim ulang OTP ke server
                    resendOtp();
                }
            }
        });


        btnVerifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                // Mendapatkan nilai dari EditText
                String otp1 = verifOtpEmail1.getText().toString().trim();
                String otp2 = verifOtpEmail2.getText().toString().trim();
                String otp3 = verifOtpEmail3.getText().toString().trim();
                String otp4 = verifOtpEmail4.getText().toString().trim();

                // Menggabungkan semua nilai OTP menjadi satu
                String otp = otp1 + otp2 + otp3 + otp4;

                // Menampilkan ProgressBar selama proses verifikasi
//                otpProgressBar.setVisibility(View.VISIBLE);

                // Melakukan verifikasi OTP melalui API
                verifikasiOtp(otp);
            }
        });

        // Periksa status verifikasi saat aplikasi dibuka
//        if (getStatusVerifikasi()) {
//            Intent intent = new Intent(this, HomeFoodyActivity.class);
//            startActivity(intent);
//            finish();
//        }

        epEmail = findViewById(R.id.email_otp);

        authToken = getAuthToken();

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<UserProfile> call = apiService.getUserProfile("Bearer " + authToken);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful()) {
                    UserProfile userProfile = response.body();
                    UserData userData = userProfile.getData();

                    epEmail.setText(userData.getEmail());

                } else {
                    // Tangani kesalahan pada respons
                    Toast.makeText(VerifikasiOtp.this, "Gagal mengambil profil. Coba lagi.", Toast.LENGTH_SHORT).show();
                    Log.e("Get Profile Error", "Gagal mengambil profil. Kode respons: " + response.code());
                    try {
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody != null) {
                            String errorJson = errorBody.string();
                            Log.e("Get Profile Error", "Detail kesalahan: " + errorJson);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                // Tangani kesalahan pada permintaan
                Toast.makeText(VerifikasiOtp.this, "Gagal mengambil profil. Periksa koneksi internet Anda.", Toast.LENGTH_SHORT).show();
                Log.e("Get Profile Error", "Gagal mengambil profil", t);
            }
        });

        // ... (Kode lainnya)

        verifOtpEmail1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Nothing to do here
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1) {
                    verifOtpEmail2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Nothing to do here
            }
        });

        verifOtpEmail2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Nothing to do here
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1) {
                    verifOtpEmail3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Nothing to do here
            }
        });

        verifOtpEmail3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Nothing to do here
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1) {
                    verifOtpEmail4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Nothing to do here
            }
        });

        verifOtpEmail4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Nothing to do here
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // You can perform any action when the text changes in the last EditText
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Nothing to do here
            }
        });



    }



    private void startCountdownTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliseconds = l;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                // Countdown timer selesai
                countDownTimer = null;

                // Perbarui teks kirim ulang dan atur ulang waktu
                kirimUlangOtp.setText("Kirim Ulang");
                timeLeftInMilliseconds = 60000; // Reset waktu
            }
        }.start();
    }

    // Fungsi untuk memperbarui teks countdown timer
    private void updateCountdownText() {
        int seconds = (int) (timeLeftInMilliseconds / 1000);
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d detik", seconds);
        kirimUlangOtp.setText(timeLeftFormatted);
    }


    // Fungsi untuk mengirim ulang OTP
    private void resendOtp() {
        // Mendapatkan token dari Shared Preferences atau sumber lainnya
        String authToken = "Bearer " + getAuthToken(); // Gantilah dengan cara yang sesuai

        // Mendapatkan instance Retrofit
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        // Melakukan request ke API /resend-otp
        Call<ApiResponse<Void>> call = apiService.resendOtp(authToken);
        call.enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<Void> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("success")) {
                        // Tindakan ketika resend OTP berhasil
                        Toast.makeText(VerifikasiOtp.this, "Kode OTP baru telah dikirim", Toast.LENGTH_SHORT).show();
                    } else {
                        // Tindakan ketika resend OTP gagal
                        Toast.makeText(VerifikasiOtp.this, "Gagal mengirim ulang OTP", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Menampilkan pesan error jika respons API tidak sukses
                    Toast.makeText(VerifikasiOtp.this, "Gagal mengirim ulang OTP", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                // Menampilkan pesan error jika terjadi kesalahan dalam melakukan request
                Toast.makeText(VerifikasiOtp.this, "Terjadi kesalahan. Silakan coba lagi.", Toast.LENGTH_SHORT).show();

                // Menampilkan detail kesalahan ke logcat
                Log.e("ResendOtpError", "Terjadi kesalahan", t);

                // Coba tambahkan untuk menangkap respons JSON dari server
                if (t instanceof HttpException) {
                    ResponseBody errorBody = ((HttpException) t).response().errorBody();
                    try {
                        String errorJson = errorBody.string();
                        Log.e("ErrorResponseBody", errorJson);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private void verifikasiOtp(String otp) {
        // Mendapatkan token dari Shared Preferences atau sumber lainnya
        String authToken = "Bearer " + getAuthToken(); // Gantilah dengan cara yang sesuai

        // Mendapatkan instance Retrofit
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        // Membuat objek verifikasiOtpModel sesuai dengan kebutuhan Anda
        // Jika diperlukan, Anda dapat mengirimkan data lain seperti email atau data lainnya
        VerifikasiOtpModel verifikasiOtpModel = new VerifikasiOtpModel(otp);

        // Melakukan request ke API /email-verification
        Call<ApiResponse<Void>> call = apiService.verifikasiOtp(authToken, verifikasiOtpModel);
        call.enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                // Menyembunyikan ProgressBar setelah mendapatkan respons dari API
//                otpProgressBar.setVisibility(View.GONE);

                // Memeriksa respons dari API
                if (response.isSuccessful()) {
                    ApiResponse<Void> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("success")) {
                        progressDialog.dismiss();
                        // Jika verifikasi sukses, simpan status dan pindah ke halaman home
//                        simpanStatusVerifikasi(true);
//                        simpanStatusVerifikasi(true);

                        saveLoginStatus(true);
                        // Simpan status login
                        saveLoginStatus(true);

                        Toast.makeText(VerifikasiOtp.this, "Verifikasi berhasil!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(VerifikasiOtp.this, HomeFoodyActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        progressDialog.dismiss();
                        // Menampilkan pesan error jika verifikasi gagal
                        Toast.makeText(VerifikasiOtp.this, "Verifikasi OTP tidak sukses", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    // Menampilkan pesan error jika respons API tidak sukses
                    Toast.makeText(VerifikasiOtp.this, "Verifikasi OTP gagal", Toast.LENGTH_SHORT).show();
                    Log.e("OTP", "Satatus: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                // Menyembunyikan ProgressBar jika terjadi kesalahan dalam melakukan request
//                otpProgressBar.setVisibility(View.GONE);

                // Menampilkan pesan error jika terjadi kesalahan
                Toast.makeText(VerifikasiOtp.this, "Terjadi kesalahan. Silakan coba lagi.", Toast.LENGTH_SHORT).show();

                // Menampilkan detail kesalahan ke logcat
                Log.e("VerifikasiOtpError", "Terjadi kesalahan", t);

                // Coba tambahkan untuk menangkap respons JSON dari server
                if (t instanceof HttpException) {
                    ResponseBody errorBody = ((HttpException) t).response().errorBody();
                    try {
                        String errorJson = errorBody.string();
                        Log.e("ErrorResponseBody", errorJson);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void logoutUser(String authToken) {
        // Mendapatkan objek ApiService
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        // Melakukan permintaan logout
        Call<Void> call = apiService.logoutUser("Bearer " + authToken);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Mendapatkan objek SharedPreferences
//                    SharedPreferences sharedPreferences = getSharedPreferences("login_status", MODE_PRIVATE);
//
//                    // Menghapus preferensi login
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.remove("is_logged_in");
//                    editor.apply();
//                    // Jika logout berhasil, pindah ke halaman hal_awal
//                    Toast.makeText(VerifikasiOtp.this, "Logout berhasil!", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(VerifikasiOtp.this, hal_awal.class);
//                    startActivity(intent);
//                    finish(); // Menutup activity saat ini agar tidak dapat kembali ke VerifikasiOtp melalui tombol back
                } else {
                    // Tangani kesalahan pada respons
//                    Toast.makeText(VerifikasiOtp.this, "Logout gagal. Coba lagi.", Toast.LENGTH_SHORT).show();
                }
                SharedPreferences sharedPreferences = getSharedPreferences("login_status", MODE_PRIVATE);

                // Menghapus preferensi login
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("is_logged_in");
                editor.apply();
                // Jika logout berhasil, pindah ke halaman hal_awal
                Toast.makeText(VerifikasiOtp.this, "Logout berhasil!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(VerifikasiOtp.this, hal_awal.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Tangani kesalahan pada permintaan
//                Toast.makeText(VerifikasiOtp.this, "Logout gagal. Periksa koneksi internet Anda.", Toast.LENGTH_SHORT).show();

                // Tambahkan log untuk melihat kesalahan pada logcat
//                Log.e("Logout Error", "Logout gagal", t);
                SharedPreferences sharedPreferences = getSharedPreferences("login_status", MODE_PRIVATE);

                // Menghapus preferensi login
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("is_logged_in");
                editor.apply();
                // Jika logout berhasil, pindah ke halaman hal_awal
                Toast.makeText(VerifikasiOtp.this, "Logout berhasil!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(VerifikasiOtp.this, hal_awal.class);
                startActivity(intent);
                finish();
            }

        });
    }

    private String getAuthToken() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("auth_token", MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }

    private void saveLoginStatus(boolean status) {
        SharedPreferences sharedPreferences = getSharedPreferences("login_status", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_logged_in", status);
        editor.apply();
    }

}
