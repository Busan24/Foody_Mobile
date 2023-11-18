package com.example.foody;

// MainActivity.java
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.foody.ApiService;
import com.example.foody.LoginRequestModel;
import com.example.foody.LoginResponseModel;
import com.example.foody.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.SharedPreferences;


public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi komponen UI
        usernameEditText = findViewById(R.id.username_login);
        passwordEditText = findViewById(R.id.password_login);

        Button loginButton = findViewById(R.id.btn_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Memanggil metode untuk melakukan login
                loginUser();
            }
        });

        TextView registerTextView = findViewById(R.id.register);

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, registrasi.class);
                startActivity(intent);
            }
        });

        TextView privacyPoliceTextView = findViewById(R.id.privacy_police);

        privacyPoliceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HomeFoodyActivity.class);
                startActivity(intent);
            }
        });
    }

    // Metode untuk melakukan login
    private void loginUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
            return;
        }

        // Membuat objek model untuk permintaan login
        LoginRequestModel loginRequestModel = new LoginRequestModel(username, password);

        // Mendapatkan objek ApiService
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        // Melakukan permintaan login
        Call<LoginResponseModel> call = apiService.loginUser(loginRequestModel);
        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                if (response.isSuccessful()) {
                    // Tangani respons yang berhasil
                    LoginResponseModel loginResponseModel = response.body();
                    String authToken = loginResponseModel.getToken();
                    // Misalnya, Anda dapat menyimpan token atau ID pengguna di sini
                    // ...
                    saveAuthToken(authToken);
                    saveLoginStatus(true);
                    // Simpan status login
                    saveLoginStatus(true);

                    // Pindah ke aktivitas selanjutnya (contoh: HomeFoodyActivity)
                    Intent intent = new Intent(MainActivity.this, HomeFoodyActivity.class);
                    startActivity(intent);
                    finish();

                    // Tampilkan notifikasi bahwa login berhasil
                    Toast.makeText(MainActivity.this, "Login berhasil!", Toast.LENGTH_SHORT).show();
                } else {
                    // Tangani kesalahan pada respons
                    if (response.code() == 401) {
                        // Kode 401 menunjukkan bahwa username atau password salah
                        Toast.makeText(MainActivity.this, "Username atau password salah. Silakan coba lagi.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Tangani kesalahan lainnya
                        Toast.makeText(MainActivity.this, "Login gagal. Periksa kredensial Anda.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                // Tangani kesalahan pada permintaan
                Toast.makeText(MainActivity.this, "Login gagal. Periksa koneksi internet Anda.", Toast.LENGTH_SHORT).show();
            }
        });
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
}
