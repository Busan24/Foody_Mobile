package com.example.foody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fitur_catatanku extends AppCompatActivity {
    Dialog myDialog;

    BottomNavigationView bottomNavigationView;

    private String authToken;
    private AutoCompleteTextView edtNamaMakanan;
    private ArrayAdapter<MakananModel> adapter;
    private List<MakananModel> daftarMakanan;

    private int catatanHariIni;

    private static final int DELAY_MILLIS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitur_catatanku);
        myDialog = new Dialog(this);

        EditText editTextJmlhPorsi = myDialog.findViewById(R.id.jmlh_porsi);

        // Inisialisasi button btn_nyatat
        Button btnNyatat = findViewById(R.id.btn_nyatat);
        btnNyatat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog("PAGI", R.drawable.pp_pink , "#131049", R.drawable.button_birucerah); // Teks "PAGI" dan latar belakang pink
            }
        });

        Button btnNyatatSiang = findViewById(R.id.btn_nyatet_siang);
        btnNyatatSiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog("SIANG", R.drawable.pp_birucerah, "#131049", R.drawable.button_merahcerah); // Teks "SIANG" dan latar belakang biru cerah
            }
        });

        Button btnNyatatSore = findViewById(R.id.btn_nyatet_sore);
        btnNyatatSore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog("SORE", R.drawable.pp_pink, "#131049", R.drawable.button_birucerah);
            }
        });

        Button btnNyatatMalam = findViewById(R.id.btn_nyatet_malam);
        btnNyatatMalam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog("MALAM", R.drawable.pp_birucerah, "#131049", R.drawable.button_merahcerah);
            }
        });

            Button btnHistory = findViewById(R.id.btn_hs_catatan);
            btnHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(fitur_catatanku.this, HistoryCatatnkuActivity.class);
                    startActivity(intent);
                }
            });


        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() { // Ganti menjadi setOnItemSelectedListener
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    // Buka halaman HomeFoodyActivity
                    Intent intent = new Intent(fitur_catatanku.this, HomeFoodyActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.nav_catatanku) {
                    // Buka halaman activity_fitur_catatanku
                    return true;
                } else if (item.getItemId() == R.id.nav_kalkulator) {
                    // Buka halaman KalkulatorBmi
                    Intent intent = new Intent(fitur_catatanku.this, KalkulatorBmi.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.nav_makanan) {
                    // Buka halaman FiturMakanan
                    Intent intent = new Intent(fitur_catatanku.this, FiturMakanan.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.nav_profil) {
                    // Buka halaman FiturProfil
                    Intent intent = new Intent(fitur_catatanku.this, FiturProfil.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
                return true;
            }
        });

        myDialog.setContentView(R.layout.popup_catatanku);

        edtNamaMakanan = myDialog.findViewById(R.id.nama_makanan);
        adapter = new ArrayAdapter<>(fitur_catatanku.this, android.R.layout.simple_dropdown_item_1line);
        edtNamaMakanan.setAdapter(adapter);

        edtNamaMakanan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                MakananModel selectedMakanan = adapter.getItem(position);
                // Lakukan apa yang perlu dilakukan dengan makanan yang dipilih
                Toast.makeText(fitur_catatanku.this, "Makanan dipilih: " + selectedMakanan.getNama(), Toast.LENGTH_SHORT).show();
            }
        });

        // ...
        CatatanMakananAdapter adapter = new CatatanMakananAdapter(new ArrayList<>());

        RecyclerView recyclerView = findViewById(R.id.rvcatatanku_daily);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // Set adapter ke RecyclerView
        recyclerView.setAdapter(adapter);

        // Tampilkan dialog


        // Panggil metode untuk mengambil data makanan dari API
        getDaftarMakanan();

        getCatatanMakananDaily();

        // Panggil metode untuk mengambil data makanan dari API
        getDaftarMakanan();



    }

    private void showCustomDialog(String text, int backgroundColor, String generateButtonTextColor, int drawable) {
        if (!getPremiumStatus() && catatanHariIni >=  3) {
            showPremiumDialog();
            return;
        }

        // Membuat dialog
        myDialog.setContentView(R.layout.popup_catatanku);

        // Mengakses elemen-elemen dalam dialog
        LinearLayout bgPopup = myDialog.findViewById(R.id.bg_popup);
        TextView txtNyatatHari = myDialog.findViewById(R.id.txt_nyatat_hari);

        // Mengubah teks dan latar belakang sesuai dengan tombol yang ditekan
        txtNyatatHari.setText(text);
        bgPopup.setBackgroundResource(backgroundColor);

        // Mengubah warna teks dan background tombol generate data makanan
        Button generateButton = myDialog.findViewById(R.id.btn_generate_makanan);
        generateButton.setBackgroundResource(drawable);
        generateButton.setTextColor(Color.parseColor(generateButtonTextColor));

        // Inisialisasi Spinner waktu_nyatat sesuai dengan waktu yang diinginkan
        Spinner spinnerWaktuNyatat = myDialog.findViewById(R.id.waktu_nyatat);
        ArrayAdapter<String> waktuAdapter = null;

        if (text.equals("PAGI")) {
            waktuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.waktu_entries));
        } else if (text.equals("SIANG")) {
            waktuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.waktu_entries_siang));
        } else if (text.equals("SORE")) {
            waktuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.waktu_entries_sore));
        } else if (text.equals("MALAM")) {
            waktuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.waktu_entries_malam));
        }

        waktuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWaktuNyatat.setAdapter(waktuAdapter);

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Atur atribut dialog (opsional)
        Window window = myDialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }

        Button btnSaveCatatan = myDialog.findViewById(R.id.btn_save_ctt);
        btnSaveCatatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Mendapatkan data dari dialog
                EditText edtNamaMakanan = myDialog.findViewById(R.id.nama_makanan);

                EditText edtJumlahKarbohidrat = myDialog.findViewById(R.id.jmlh_karbohidrat);
                EditText edtJumlahProtein = myDialog.findViewById(R.id.jmlh_protein);
                EditText edtJumlahLemak = myDialog.findViewById(R.id.jmlh_lemak);
                EditText edtJumlahGula = myDialog.findViewById(R.id.jmlh_gula);
                EditText edtJumlahGaram = myDialog.findViewById(R.id.jmlh_garam);
                EditText edtJumlahPorsi = myDialog.findViewById(R.id.jmlh_porsi);

                Spinner spinnerWaktuNyatat = myDialog.findViewById(R.id.waktu_nyatat);


                String namaMakanan = edtNamaMakanan.getText().toString();
                String waktuMakan = spinnerWaktuNyatat.getSelectedItem().toString();
                String jumlahKarbohidratStr = edtJumlahKarbohidrat.getText().toString();
                String jumlahProteinStr = edtJumlahProtein.getText().toString();
                String jumlahLemakStr = edtJumlahLemak.getText().toString();
                String jumlahGulaStr = edtJumlahGula.getText().toString();
                String jumlahGaramStr = edtJumlahGaram.getText().toString();
                String jumlahPorsiStr = edtJumlahPorsi.getText().toString();

                if (TextUtils.isEmpty(namaMakanan)) {
                    edtNamaMakanan.setError("Nama Makanan harus diisi");
                    Toast.makeText(fitur_catatanku.this, "Nama Makanan harus diisi", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(jumlahKarbohidratStr)) {
                    edtJumlahKarbohidrat.setError("Jumlah Porsi harus diisi");
                    Toast.makeText(fitur_catatanku.this, "Jumlah Karbohidrat harus diisi", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(jumlahProteinStr)) {
                    edtJumlahProtein.setError("Jumlah Porsi harus diisi");
                    Toast.makeText(fitur_catatanku.this, "Jumlah Protein harus diisi", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(jumlahLemakStr)) {
                    edtJumlahLemak.setError("Jumlah Porsi harus diisi");
                    Toast.makeText(fitur_catatanku.this, "Jumlah Lemak harus diisi", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(jumlahGulaStr)) {
                    edtJumlahGula.setError("Jumlah Porsi harus diisi");
                    Toast.makeText(fitur_catatanku.this, "Jumlah Gula harus diisi", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(jumlahGaramStr)) {
                    edtJumlahGaram.setError("Jumlah Porsi harus diisi");
                    Toast.makeText(fitur_catatanku.this, "Jumlah Garam harus diisi", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(jumlahPorsiStr)) {
                    edtJumlahPorsi.setError("Jumlah Porsi harus diisi");
                    Toast.makeText(fitur_catatanku.this, "Jumlah Porsi harus diisi", Toast.LENGTH_SHORT).show();
                    return;
                }

                Double jumlahKarbohidrat = Double.valueOf(jumlahKarbohidratStr);
                Double jumlahProtein = Double.valueOf(jumlahProteinStr);
                Double jumlahLemak = Double.valueOf(jumlahLemakStr);
                Double jumlahGula = Double.valueOf(jumlahGulaStr);
                Double jumlahGaram = Double.valueOf(jumlahGaramStr);
                int jumlahPorsi = Integer.parseInt(jumlahPorsiStr);

                // Buat objek CatatanMakananModel
                CatatanMakananModel catatanMakanan = new CatatanMakananModel();
                catatanMakanan.setNama(namaMakanan);

                catatanMakanan.setKarbohidrat(jumlahKarbohidrat);
                catatanMakanan.setProtein(jumlahProtein);
                catatanMakanan.setLemak(jumlahLemak);
                catatanMakanan.setGula(jumlahGula);
                catatanMakanan.setGaram(jumlahGaram);

                catatanMakanan.setWaktu(waktuMakan);
                catatanMakanan.setJumlah(jumlahPorsi);

                // Panggil Retrofit untuk menyimpan catatan makanan
                simpanCatatanMakanan(catatanMakanan);
            }
        });

        Button btnGenerateMakanan = myDialog.findViewById(R.id.btn_generate_makanan);
        btnGenerateMakanan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText edtNamaMakanan = myDialog.findViewById(R.id.nama_makanan);
                String namaMakanan = edtNamaMakanan.getText().toString();

//                Validasi apakah nama makanan sudah diisi atau belum
                if (TextUtils.isEmpty(namaMakanan)) {
                    edtNamaMakanan.setError("Nama Makanan harus diisi");
                    Toast.makeText(fitur_catatanku.this, "Nama Makanan harus diisi", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Menampilkan dialog loading
                Dialog loadingDialog = new Dialog(fitur_catatanku.this);
                loadingDialog.setContentView(R.layout.dialog_loading);

                // Atur atribut dialog (opsional)
                Window window = loadingDialog.getWindow();
                if (window != null) {
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                }

                // Menampilkan loading dialog
                loadingDialog.show();

                GenerateMakananRequestModel generateMakananRequestModel = new GenerateMakananRequestModel(namaMakanan);

                ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
                String authToken = "Bearer " + getAuthToken();

                Call<ApiResponse<MakananModel>> call = apiService.generateMakanan(authToken, generateMakananRequestModel);
                call.enqueue(new Callback<ApiResponse<MakananModel>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<MakananModel>> call, Response<ApiResponse<MakananModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            MakananModel makananModel = response.body().getData();

                            EditText edtJumlahKarbohidrat = myDialog.findViewById(R.id.jmlh_karbohidrat);
                            EditText edtJumlahProtein = myDialog.findViewById(R.id.jmlh_protein);
                            EditText edtJumlahLemak = myDialog.findViewById(R.id.jmlh_lemak);
                            EditText edtJumlahGula = myDialog.findViewById(R.id.jmlh_gula);
                            EditText edtJumlahGaram = myDialog.findViewById(R.id.jmlh_garam);

                            edtJumlahKarbohidrat.setText(String.valueOf(makananModel.getKarbohidrat()));
                            edtJumlahProtein.setText(String.valueOf(makananModel.getProtein()));
                            edtJumlahLemak.setText(String.valueOf(makananModel.getLemak()));
                            edtJumlahGula.setText(String.valueOf(makananModel.getGula()));
                            edtJumlahGaram.setText(String.valueOf(makananModel.getGaram()));

                            // Menutup loading dialog dengan penundaan
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loadingDialog.dismiss();

                                    // Tampilkan dialog sukses setelah penutupan dialog loading
//                                    showSuccessDialog();

                                }
                            }, DELAY_MILLIS);


                        }

                        else {
                            loadingDialog.dismiss();

                            showFailedDialog();
                            Toast.makeText(fitur_catatanku.this, "Gagal mendapatkan data kandungan makanan", Toast.LENGTH_SHORT).show();
                            Log.e("RetrofitError", "Response code: " + response.code());
                            Log.e("RetrofitError", "Error body: " + response.body());
                        }

                    }

                    public void  onFailure(Call<ApiResponse<MakananModel>> call, Throwable t) {
                        loadingDialog.dismiss();
                        Log.e("RetrofitError", "Error: " + t.getMessage(), t);
                    }

                });

            }

        });

        myDialog.show();

    }

    private void simpanCatatanMakanan(CatatanMakananModel catatanMakanan) {
        // Menampilkan dialog loading
//        Dialog loadingDialog = new Dialog(fitur_catatanku.this);
//        loadingDialog.setContentView(R.layout.dialog_loading);

        // Atur atribut dialog (opsional)
//        Window window = loadingDialog.getWindow();
//        if (window != null) {
//            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        }

        // Menampilkan loading dialog
//        loadingDialog.show();

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        String authToken = "Bearer " + getAuthToken();


        Call<ApiResponse<CatatanMakananModel>> call = apiService.simpanCatatanMakanan(authToken, catatanMakanan);
        call.enqueue(new Callback<ApiResponse<CatatanMakananModel>>() {
            @Override
            public void onResponse(Call<ApiResponse<CatatanMakananModel>> call, Response<ApiResponse<CatatanMakananModel>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    getCatatanMakananDaily();
                    myDialog.dismiss();

                } else {
                    showFailedDialog();
//                    loadingDialog.dismiss();
                    myDialog.dismiss();
                    // Gagal disimpan
                    Toast.makeText(fitur_catatanku.this, "Gagal Menyimpan Catatan Makanan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<CatatanMakananModel>> call, Throwable t) {
                // Menutup loading dialog ketika terjadi kesalahan

                // Gagal disimpan
                Toast.makeText(fitur_catatanku.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                Log.e("RetrofitError", "Error: " + t.getMessage(), t);

                showFailedDialog();
//                loadingDialog.dismiss();
                myDialog.dismiss();
            }
        });
    }

//    private MakananModel getGeneratedMakanan(String makanan, String detail) {
//        GenerateMakananRequestModel generateMakananRequestModel = new GenerateMakananRequestModel(makanan, detail);
//
//        // Menampilkan dialog loading
//        Dialog loadingDialog = new Dialog(fitur_catatanku.this);
//        loadingDialog.setContentView(R.layout.dialog_loading);
//
//        // Atur atribut dialog (opsional)
//        Window window = loadingDialog.getWindow();
//        if (window != null) {
//            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        }
//
//        // Menampilkan loading dialog
//        loadingDialog.show();
//
//        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
//        String authToken = "Bearer " + getAuthToken();
//
//        Call<ApiResponse<MakananModel>> call = apiService.generateMakanan("Bearer " + authToken, generateMakananRequestModel);
//        call.enqueue(new Callback<ApiResponse<MakananModel>>() {
//            @Override
//            public void onResponse(Call<ApiResponse<MakananModel>> call1, Response<ApiResponse<MakananModel>> response) {
//                if (response.isSuccessful()) {
//                    ApiResponse<MakananModel> apiResponse = response.body();
//                    MakananModel makananModel = apiResponse.getData();
//
//                    // Menutup loading dialog dengan penundaan
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            loadingDialog.dismiss();
//
//                            // Tampilkan dialog sukses setelah penutupan dialog loading
//                            showSuccessDialog();
//
//                            getCatatanMakananDaily();
//
//                            myDialog.dismiss();
//
//
//                        }
//                    }, DELAY_MILLIS);
//
//                    return makananModel;
//                }
//
//                else {
//                    loadingDialog.dismiss();
//                    Toast.makeText(fitur_catatanku.this, "Gagal mendapatkan data kandungan makanan", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            public void  onFailure(Call<ApiResponse<MakananModel>> call, Throwable t) {
//                loadingDialog.dismiss();
//                Log.e("RetrofitError", "Error: " + t.getMessage(), t);
//            }
//
//        });
//    }

    private void getSummaryData(){
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        String authToken = "Bearer " + getAuthToken();

        Call<ApiResponse<SummaryData>> callSummary = apiService.getUserSummary("Bearer " + authToken);
        callSummary.enqueue(new Callback<ApiResponse<SummaryData>>() {
            @Override
            public void onResponse(Call<ApiResponse<SummaryData>> call, Response<ApiResponse<SummaryData>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<SummaryData> apiResponseSummary = response.body();
                    if (apiResponseSummary != null && "success".equals(apiResponseSummary.getStatus())) {
                        SummaryData summaryData = apiResponseSummary.getData();
                        if (summaryData != null) {
                            if (summaryData.getDaily_garam() > summaryData.getBatas_garam()){
                                showWarningDialog("garam");
                            }if (summaryData.getDaily_gula() > summaryData.getBatas_gula()){
                                showWarningDialog("gula");
                            }if (summaryData.getDaily_karbohidrat() > summaryData.getBatas_karbohidrat()){
                                showWarningDialog("karbohidrat");
                            }if (summaryData.getDaily_protein() > summaryData.getBatas_protein()){
                                showWarningDialog("protein");
                            }if (summaryData.getDaily_lemak() > summaryData.getBatas_lemak()){
                                showWarningDialog("lemak");
                            }

                        } else {
                            // Handle jika data summary tidak ditemukan
                        }
                    } else {

                    }
                } else {
                    // Handle jika terjadi kesalahan saat mengambil data summary
                    ;
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<SummaryData>> call, Throwable t) {

            }
        });

    }

    private void showWarningDialog(String kandungan) {
        // Create a dialog
        Dialog warningDialog = new Dialog(fitur_catatanku.this);
        warningDialog.setContentView(R.layout.activity_notif_peringatan);

        warningDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Access elements in the dialog
        TextView peringatanKandungan = warningDialog.findViewById(R.id.peringatan_kandungan);
        TextView solusiKandungan = warningDialog.findViewById(R.id.solusi_kandungan);
        Button gagalCloseButton = warningDialog.findViewById(R.id.gagal_close);

        // Set text based on the exceeded values
        peringatanKandungan.setText(kandungan);
        solusiKandungan.setText(kandungan);

        // Close the warning dialog
        gagalCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warningDialog.dismiss();
            }
        });

        // Show the warning dialog
        warningDialog.show();
    }


    private void getCatatanMakananDaily() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        String authToken = "Bearer " + getAuthToken();

        Call<ApiResponse<List<CatatanMakananModel>>> call = apiService.getCatatanMakananDaily(authToken);
        call.enqueue(new Callback<ApiResponse<List<CatatanMakananModel>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<CatatanMakananModel>>> call, Response<ApiResponse<List<CatatanMakananModel>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CatatanMakananModel> catatanMakananList = response.body().getData();
                    // Tampilkan data ke RecyclerView
                    catatanHariIni = catatanMakananList.size();
                    displayCatatanMakanan(catatanMakananList);
                } else {
                    // Handle kesalahan respons
                    Toast.makeText(fitur_catatanku.this, "Gagal mendapatkan catatan makanan harian", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<CatatanMakananModel>>> call, Throwable t) {
                // Handle kesalahan jaringan
                Toast.makeText(fitur_catatanku.this, "Terjadi kesalahan jaringan", Toast.LENGTH_SHORT).show();
                Log.e("RetrofitError", "Error: " + t.getMessage(), t);
            }
        });
    }

    private void displayCatatanMakanan(List<CatatanMakananModel> catatanMakananList) {
        // Inisialisasi RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvcatatanku_daily);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Buat objek adapter untuk RecyclerView
        CatatanMakananAdapter adapter = new CatatanMakananAdapter(catatanMakananList);
        recyclerView.setAdapter(adapter);

        

        // Tambahkan baris berikut untuk memastikan adapter diupdate
        adapter.notifyDataSetChanged();

        adapter.setOnDeleteClickListener(new CatatanMakananAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int position, String catatanId) {
                showDeleteConfirmationDialog(catatanId);
            }
        });
    }


    private String getAuthToken() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("auth_token", MODE_PRIVATE);
        String authToken = sharedPreferences.getString("token", "");
        Log.d("AuthToken", "Token: " + authToken); // Tambahkan log ini
        return authToken;
    }

    private void getDaftarMakanan() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<ApiResponse<List<MakananModel>>> call = apiService.getMakanan("Bearer " + getAuthToken());

        call.enqueue(new Callback<ApiResponse<List<MakananModel>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<MakananModel>>> call, Response<ApiResponse<List<MakananModel>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    daftarMakanan = response.body().getData();
                    adapter.addAll(daftarMakanan);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<MakananModel>>> call, Throwable t) {
                Log.e("API Error", "Gagal mengambil data makanan: " + t.getMessage());
            }
        });
    }

    private void showSuccessDialog() {
        // Membuat dialog
        Dialog successDialog = new Dialog(fitur_catatanku.this);
        Dialog loadingDialog = new Dialog(fitur_catatanku.this);
        successDialog.setContentView(R.layout.activity_dialog_success);

        Button successCloseButton = successDialog.findViewById(R.id.success_close);
        successCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tutup dialog sukses
                successDialog.dismiss();

                // Tutup dialog loading
                loadingDialog.dismiss();

                // Tutup dialog login
                myDialog.dismiss();

                getSummaryData();
            }
        });

        // Tampilkan dialog berhasil
        successDialog.show();
    }

    private void showFailedDialog() {
        Dialog failedDialog = new Dialog(fitur_catatanku.this);
        failedDialog.setContentView(R.layout.activity_dialog_gagal);

        Button failedCloseButton = failedDialog.findViewById(R.id.gagal_close);
        failedCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tutup dialog gagal
                failedDialog.dismiss();
            }
        });

        // Tampilkan dialog gagal
        failedDialog.show();
    }

    private void showDeleteConfirmationDialog(String catatanId) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_hapus);

        // ... (inisialisasi elemen-elemen dalam dialog)

        // Ketika tombol "Hapus" pada dialog diklik
        Button hapusButton = dialog.findViewById(R.id.hapus_catatanku);
        hapusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil metode untuk melakukan penghapusan dari server
                hapusCatatan(catatanId);
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



    private void hapusCatatan(String catatanId) {
        // Panggil API untuk menghapus catatan
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        String authToken = "Bearer " + getAuthToken();

        Call<ApiResponse<Void>> call = apiService.hapusCatatanMakanan(authToken, catatanId);
        call.enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Berhasil dihapus
                    Toast.makeText(fitur_catatanku.this, "Catatan Makanan Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                    getCatatanMakananDaily(); // Refresh tampilan setelah penghapusan
                } else {
                    // Gagal dihapus
                    Toast.makeText(fitur_catatanku.this, "Gagal Menghapus Catatan Makanan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                // Handle kesalahan jaringan
                Toast.makeText(fitur_catatanku.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                Log.e("RetrofitError", "Error: " + t.getMessage(), t);
            }
        });
    }

    private void showPremiumDialog() {
        BottomSheetDialog premiumDialog = new BottomSheetDialog(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.popup_premium, null);

        // Atur layout untuk tampil full screen
        premiumDialog.setContentView(dialogView);
        BottomSheetBehavior<FrameLayout> behavior = premiumDialog.getBehavior();
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED); // Full screen
        behavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO, true);

        // Mencegah dialog ditutup saat di-scroll
        behavior.setHideable(false); // Dialog tidak dapat ditutup dengan scroll
        behavior.setDraggable(false); // Nonaktifkan scroll dialog

        // Mengubah layout root agar tinggi menjadi full screen
        ViewGroup.LayoutParams params = dialogView.getLayoutParams();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT; // Full screen height
        dialogView.setLayoutParams(params);

        // Menghapus bayangan (dimming) di belakang dialog
        if (premiumDialog.getWindow() != null) {
            premiumDialog.getWindow().setDimAmount(0f); // Tanpa bayangan
            premiumDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        // Temukan TextView dan Button dalam layout
        TextView btnTutup = dialogView.findViewById(R.id.text_tutup);
        Button btnUpgrade = dialogView.findViewById(R.id.btn_upgrade);

        btnTutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog when close button is clicked
                premiumDialog.dismiss();
            }
        });

        btnUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(fitur_catatanku.this, PremiumActivity.class));
            }
        });

        // Menampilkan dialog
        premiumDialog.show();
    }

    private boolean getPremiumStatus() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("premium_status", MODE_PRIVATE);
        boolean premium = sharedPreferences.getBoolean("is_premium", false);
        Log.d("Premium", "status: " + premium); // Tambahkan log ini
        return premium;
    }

}
