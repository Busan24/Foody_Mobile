package com.example.foody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.LayoutInflater;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout;
import android.app.Dialog;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.graphics.drawable.Drawable;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.List;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KalkulatorBmi extends AdsActivity implements BmiRecentAdapter.OnDeleteClickListener {

    BottomNavigationView bottomNavigationView;
    Dialog dialog;
    private  EditText  etTinggiBadan, etBeratBadan;

    private RecyclerView rvBmiRecent;
    private BmiRecentAdapter bmiRecentAdapter;

    private ImageView gambar;

    private WebView webView;

    private int bmiHariIni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_kalkulator_bmi, findViewById(R.id.content_frame));
//        setContentView(R.layout.activity_kalkulator_bmi);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.nav_kalkulator);

//        webView = findViewById(R.id.webView);
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setBuiltInZoomControls(false);
//        webView.getSettings().setDisplayZoomControls(false);
//
        showBmiChart();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    Intent intent = new Intent(KalkulatorBmi.this, HomeFoodyActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.nav_catatanku) {
                    Intent intent = new Intent(KalkulatorBmi.this, fitur_catatanku.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.nav_kalkulator) {
                    return true;
                } else if (item.getItemId() == R.id.nav_makanan) {
                    Intent intent = new Intent(KalkulatorBmi.this, FiturMakanan.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.nav_profil) {
                    Intent intent = new Intent(KalkulatorBmi.this, FiturProfil.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
                return true;
            }
        });

        // Set onClickListener for sd_ket1
        TextView sdKet1 = findViewById(R.id.sd_ket1);
        sdKet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the dialog
                showCustomDialog();
            }
        });

        // Set onClickListener for as_bmi
        ImageView asBmi = findViewById(R.id.as_bmi);
        asBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the dialog
                showCustomDialog();
            }
        });

        Button btnCount = findViewById(R.id.btn_count);
        EditText etTinggiBadan = findViewById(R.id.isi_tb);
        EditText etBeratBadan = findViewById(R.id.isi_bb);


        ScrollView scrollView = findViewById(R.id.scrollView);

        btnCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitialAd();

                if (!getPremiumStatus() && bmiHariIni >= 1) {
                    showPremiumDialog();
                    return;
                }

                // Ambil tinggi badan dan berat badan dari EditText
                if (validateInput(etTinggiBadan) && validateInput(etBeratBadan)) {
                    double tinggiBadan = Double.parseDouble(etTinggiBadan.getText().toString());
                    double beratBadan = Double.parseDouble(etBeratBadan.getText().toString());
                    BmiModel bmiModel = new BmiModel(tinggiBadan, beratBadan);

                    sendBmiToServer(bmiModel);
                    closeKeyboard();
//                    scrollView.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            scrollView.fullScroll(View.FOCUS_DOWN);
//                        }
//                    });
                } else {
                    Toast.makeText(KalkulatorBmi.this, "Isi Tinggi dan Berat Badan terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnReset = findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset input fields
                etTinggiBadan.setText("");
                etBeratBadan.setText("");
                closeKeyboard();
            }
        });

        Button btnHistory = findViewById(R.id.btn_hs_bmi);
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset input fields
                showInterstitialAdAndNavigate(BmiHistory.class);
            }
        });

        rvBmiRecent = findViewById(R.id.rvbmi_recent);
        bmiRecentAdapter = new BmiRecentAdapter();
        rvBmiRecent.setAdapter(bmiRecentAdapter);
        rvBmiRecent.setLayoutManager(new LinearLayoutManager(this));

        // Fetch recent BMI data
        fetchRecentBmiData();

        bmiRecentAdapter.setOnDeleteClickListener(this);

//        getBmiChart();
    }

//    private void getBmiChartData() {
//        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
//        String authToken = "Bearer " + getAuthToken();
//
//        Call<ApiResponse<ChartData>> callChart = apiService.getBmiChartData(authToken);
//        callChart.enqueue(new Callback<ApiResponse<ChartData>>() {
//            @Override
//            public void onResponse(Call<ApiResponse<ChartData>> call, Response<ApiResponse<ChartData>> response) {
//                if (response.isSuccessful()) {
//                    ApiResponse<ChartData> apiResponse = response.body();
//
//                    if (apiResponse != null && apiResponse.getStatus().equals("success")) {
//                        ChartData chartUrl =  apiResponse.getData();
//                        String link = "https://youtube.com";
//                        if (chartUrl != null) {
//                            // Tampilkan chart di WebView
//                            showChartInWebView(link);
//                        } else {
//                            Log.e("BmiChart", "Error: Unexpected chart data format");
//                        }
//                    } else {
//                        Log.e("BmiChart", "Error: Unexpected response format");
//                    }
//                } else {
//                    String rawResponse = response.raw().toString();
//                    Log.d("BmiChart", "Raw Response: " + rawResponse);
//                    Log.e("BmiChart", "Error: " + response.message());
//                }
//            }
//
//
//            @Override
//            public void onFailure(Call<ApiResponse<ChartData>> call, Throwable t) {
//                Log.e("BmiChart", "Network failure: " + t.getMessage());
//            }
//        });
//    }

//    private void getBmiChart(){
//        String authToken = "Bearer " + getAuthToken();
//        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
//        Call<ApiResponse<ChartData>> callChart = apiService.getBmiChartData(authToken);
//
//        callChart.enqueue(new Callback<ApiResponse<ChartData>>() {
//            @Override
//            public void onResponse(Call<ApiResponse<ChartData>> call, Response<ApiResponse<ChartData>> response) {
//                if (response.isSuccessful()) {
//                    ApiResponse<ChartData> apiResponse = response.body();
//                    if (apiResponse != null && "success".equals(apiResponse.getStatus())) {
//                        ChartData chart = apiResponse.getData();
//                        if (chart != null) {
//                            String link = chart.getLink();
//                            showChartInWebView(link);
//                        }
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse<ChartData>> call, Throwable t) {
//                Log.e("BmiChart", "Network failure: " + t.getMessage());
//            }
//        });
//    }


    @Override
    public void onDeleteClick(int position, BmiHistoryModel bmi) {
        // Tampilkan dialog konfirmasi penghapusan
        showDeleteConfirmationDialog(position, bmi);
    }

    private void deleteBmiData(int position, BmiHistoryModel bmi) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        String authToken = "Bearer " + getAuthToken();

        String bmiId = bmi.getId(); // Menggunakan String untuk ID

        Call<ApiResponse<Void>> call = apiService.deleteBmiData(authToken, bmiId);
        call.enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<Void> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus().equals("success")) {
                        // Hapus item dari RecyclerView
                        bmiRecentAdapter.removeBmiItem(position);
                        Toast.makeText(KalkulatorBmi.this, "Data BMI berhasil dihapus", Toast.LENGTH_SHORT).show();
                        showBmiChart();
                    } else {
                        Toast.makeText(KalkulatorBmi.this, "Gagal menghapus data BMI", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(KalkulatorBmi.this, "Gagal menghapus data BMI", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                Log.e("API Error", "Gagal menghubungi server: " + t.getMessage());
                Toast.makeText(KalkulatorBmi.this, "Terjadi kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void showDeleteConfirmationDialog(int position, BmiHistoryModel bmi) {
        // Implementasi tampilan dialog konfirmasi penghapusan
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_hapus);

        // Mengatur latar belakang dialog agar transparan
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // Atur atribut dialog (opsional)
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }

        // Ketika tombol "Hapus" pada dialog diklik
        Button hapusButton = dialog.findViewById(R.id.hapus_catatanku);
        hapusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil metode untuk melakukan penghapusan dari server
                deleteBmiData(position, bmi);
                dialog.dismiss();
            }
        });

        // Ketika tombol "Batal" pada dialog diklik
        Button batalButton = dialog.findViewById(R.id.batal_hapus);
        batalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void fetchRecentBmiData() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        String authToken = "Bearer " + getAuthToken();

        Call<ApiResponse<List<BmiHistoryModel>>> call = apiService.getRecentBmiData(authToken);
        call.enqueue(new Callback<ApiResponse<List<BmiHistoryModel>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<BmiHistoryModel>>> call, Response<ApiResponse<List<BmiHistoryModel>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<BmiHistoryModel>> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getData() != null) {
                        List<BmiHistoryModel> bmiList = apiResponse.getData();
                        // Update RecyclerView with the new data
                        bmiRecentAdapter.setBmiList(bmiList);
                        bmiHariIni = Integer.valueOf(apiResponse.getMessage());
                    }

                    else {
                        Toast.makeText(KalkulatorBmi.this, "Data null in response body", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(KalkulatorBmi.this, "Failed to fetch recent BMI data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<BmiHistoryModel>>> call, Throwable t) {
                Log.e("API Error", "Failed to contact the server: " + t.getMessage());
                Toast.makeText(KalkulatorBmi.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showBmiChart() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        String authToken = "Bearer " + getAuthToken();

        Call<ApiResponse<Void>> call = apiService.getBmiChart(authToken);
        call.enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    String link = apiResponse.getMessage();

                        ImageView imageView = findViewById(R.id.imageView);
                        Glide.with(KalkulatorBmi.this)
                                .load(link)
                                .into(imageView);
                }
            };

            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                Log.e("API Error", "Failed to contact the server: " + t.getMessage());
                Toast.makeText(KalkulatorBmi.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showCustomDialog() {
        // Create a custom dialog
        dialog = new Dialog(KalkulatorBmi.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_ketbmi1);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView bbClose = dialog.findViewById(R.id.bb_close);
        bbClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog when bb_close is clicked
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    // ...
    private void sendBmiToServer(BmiModel bmiModel) {
        EditText etTinggiBadan = findViewById(R.id.isi_tb);
        EditText etBeratBadan = findViewById(R.id.isi_bb);
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        String authToken = "Bearer " + getAuthToken();

        Call<ApiResponse<BmiModel>> call = apiService.hitungBmi(authToken, bmiModel);
        call.enqueue(new Callback<ApiResponse<BmiModel>>() {
            @Override
            public void onResponse(Call<ApiResponse<BmiModel>> call, Response<ApiResponse<BmiModel>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<BmiModel> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getData() != null) {
                        BmiModel bmiResponse = apiResponse.getData();

                        // Display notification dialog
                        showBmiNotification(bmiResponse);
                        showBmiChart();

                        fetchRecentBmiData();
                        etTinggiBadan.setText("");
                        etBeratBadan.setText("");
                    } else {
                        Toast.makeText(KalkulatorBmi.this, "Data null in response body", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Parsing error body menjadi ApiError<String>
                    try {
                        // Gunakan Gson untuk parsing error body
                        Gson gson = new Gson();
                        ApiError<String> apiError = gson.fromJson(response.errorBody().charStream(), ApiError.class);

                        // Menampilkan pesan error
                        Toast.makeText(KalkulatorBmi.this, apiError.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        Toast.makeText(KalkulatorBmi.this, "Failed to parse error response", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<BmiModel>> call, Throwable t) {
                Log.e("API Error", "Gagal menghubungi server: " + t.getMessage());
                Toast.makeText(KalkulatorBmi.this, "Terjadi kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showBmiNotification(BmiModel bmiModel) {
        // Create a custom dialog
        dialog = new Dialog(KalkulatorBmi.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_notif_bmi);

        // Set values from API response
        TextView hasilBmiNotif = dialog.findViewById(R.id.hasil_bmi_notif);
        TextView kategoriBmi = dialog.findViewById(R.id.kategori_bmi);
        hasilBmiNotif.setText(String.valueOf(bmiModel.getNilai_bmi()));
        kategoriBmi.setText(bmiModel.getKategori());

        // Set background color
        CardView backgroundNotifBmi = dialog.findViewById(R.id.background_notif_bmi);

        // Periksa null sebelum menggunakan warna tebal
        if (bmiModel.getWarna() != null) {
            backgroundNotifBmi.setCardBackgroundColor(Color.parseColor(bmiModel.getWarna()));
        } else {
            Log.e("Color Error", "WarnaTebal is null");
        }

        // Set text color
        hasilBmiNotif.setTextColor(Color.parseColor(bmiModel.getWarna_tebal()));
        kategoriBmi.setTextColor(Color.parseColor(bmiModel.getWarna_tebal()));

        // Set onClickListener for success_close button
        Button successClose = dialog.findViewById(R.id.success_close);
        successClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog when success_close is clicked
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private String getAuthToken() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("auth_token", MODE_PRIVATE);
        String authToken = sharedPreferences.getString("token", "");
        Log.d("AuthToken", "Token: " + authToken); // Tambahkan log ini
        return authToken;
    }


    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private boolean validateInput(EditText editText) {
        String input = editText.getText().toString().trim();
        return !input.isEmpty();
    }

    public void HalamanHistoryBmi(View view) {
        Intent intent = new Intent(this, BmiHistory.class);
        startActivity(intent);
    }

}
