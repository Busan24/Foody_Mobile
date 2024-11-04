package com.orion.foody;

import android.os.Bundle;

import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import android.content.Intent;
import android.content.SharedPreferences;

import android.view.View;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Converter;

import okhttp3.ResponseBody;

import java.lang.annotation.Annotation;

public class ResetPasswordActivity extends AdsActivity {
    EditText eTcurrentPassword, eTnewPassword, eTnewPassowrdConfirmation;
    Button btnSubmit, buttonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_reset_password, findViewById(R.id.content_frame));
//        setContentView(R.layout.activity_reset_password);

        buttonBack = findViewById(R.id.btn_close);

        buttonBack.setOnClickListener(view -> {
            finish();
        });

        eTcurrentPassword = findViewById(R.id.current_password);
        eTnewPassword = findViewById(R.id.new_password);
        eTnewPassowrdConfirmation = findViewById(R.id.new_password_confirmation);
        btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String currentPassword = eTcurrentPassword.getText().toString().trim();
                String newPassword = eTnewPassword.getText().toString().trim();
                String newPasswordConfirmation = eTnewPassowrdConfirmation.getText().toString().trim();

                if (currentPassword.isEmpty() || newPassword.isEmpty() || newPasswordConfirmation.isEmpty()) {
                    Toast.makeText(ResetPasswordActivity.this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (newPassword.length() < 8) {
                    eTnewPassword.setError("Password harus memiliki panjang minimal 8 karakter");
                    return;
                }

                if (!newPasswordConfirmation.equals(newPassword)) {
                    eTnewPassowrdConfirmation.setError("Password konfirmasi tidak sesuai");
                    return;
                }

                changePassword(currentPassword, newPassword, newPasswordConfirmation);
            }
        });

    }

    private void changePassword(String currentPassword, String newPassword, String newPasswordConfirmation) {
        ResetPasswordRequest  resetPasswordRequest = new ResetPasswordRequest(currentPassword, newPassword, newPasswordConfirmation);

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<ApiResponse<UserData>> call = apiService.changePassword("Bearer " + getAuthToken(),  resetPasswordRequest);
        call.enqueue(new Callback<ApiResponse<UserData>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserData>> call, Response<ApiResponse<UserData>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ResetPasswordActivity.this, "Berhasil merubah password", Toast.LENGTH_SHORT).show();
                    finish();
                }

                else {
                    try {
                        // Mengonversi errorBody menjadi ApiResponse
                        Converter<ResponseBody, ApiResponse> converter =
                                RetrofitClient.getClient().responseBodyConverter(ApiResponse.class, new Annotation[0]);
                        ApiResponse errorResponse = converter.convert(response.errorBody());

                        // Menampilkan pesan kesalahan dari errorResponse
                        Toast.makeText(ResetPasswordActivity.this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("API", "Error: " + response.code() + " - " + errorResponse.getMessage());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<UserData>> call, Throwable t) {
                // Handle kesalahan jaringan atau server
                Toast.makeText(ResetPasswordActivity.this, "Terjadi kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String getAuthToken() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("auth_token", MODE_PRIVATE);
        String authToken = sharedPreferences.getString("token", "");
        Log.d("AuthToken", "Token: " + authToken); // Tambahkan log ini
        return authToken;
    }
}