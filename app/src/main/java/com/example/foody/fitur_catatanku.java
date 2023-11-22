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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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
                showCustomDialog("PAGI", "#FDCED0"); // Teks "PAGI" dan latar belakang pink
            }
        });

        Button btnNyatatSiang = findViewById(R.id.btn_nyatet_siang);
        btnNyatatSiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog("SIANG", "#D9F4FF"); // Teks "SIANG" dan latar belakang biru cerah
            }
        });

        Button btnNyatatSore = findViewById(R.id.btn_nyatet_sore);
        btnNyatatSore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog("SORE", "#FDCED0");
            }
        });

        Button btnNyatatMalam = findViewById(R.id.btn_nyatet_malam);
        btnNyatatMalam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog("MALAM", "#D9F4FF");
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

        Button btnRecent = findViewById(R.id.btn_recent);
        btnRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fitur_catatanku.this, ViewData.class);
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

    private void showCustomDialog(String text, String backgroundColor) {
        // Membuat dialog
        myDialog.setContentView(R.layout.popup_catatanku);

        // Mengakses elemen-elemen dalam dialog
        LinearLayout bgPopup = myDialog.findViewById(R.id.bg_popup);
        TextView txtNyatatHari = myDialog.findViewById(R.id.txt_nyatat_hari);

        // Mengubah teks dan latar belakang sesuai dengan tombol yang ditekan
        txtNyatatHari.setText(text);
        bgPopup.setBackgroundColor(Color.parseColor(backgroundColor));

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
                Spinner spinnerWaktuNyatat = myDialog.findViewById(R.id.waktu_nyatat);
                EditText edtJumlahPorsi = myDialog.findViewById(R.id.jmlh_porsi);

                String namaMakanan = edtNamaMakanan.getText().toString();
                String waktuMakan = spinnerWaktuNyatat.getSelectedItem().toString();
                int jumlahPorsi = Integer.parseInt(edtJumlahPorsi.getText().toString());

                // Buat objek CatatanMakananModel
                CatatanMakananModel catatanMakanan = new CatatanMakananModel();
                catatanMakanan.setNama(namaMakanan);
                catatanMakanan.setWaktu(waktuMakan);
                catatanMakanan.setJumlah(jumlahPorsi);

                // Panggil Retrofit untuk menyimpan catatan makanan
                simpanCatatanMakanan(catatanMakanan);
            }
        });

        // Instansiasi adapter
        myDialog.show();
    }



    // ...

    private void simpanCatatanMakanan(CatatanMakananModel catatanMakanan) {
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

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        String authToken = "Bearer " + getAuthToken();

        Call<ApiResponse<CatatanMakananModel>> call = apiService.simpanCatatanMakanan(authToken, catatanMakanan);
        call.enqueue(new Callback<ApiResponse<CatatanMakananModel>>() {
            @Override
            public void onResponse(Call<ApiResponse<CatatanMakananModel>> call, Response<ApiResponse<CatatanMakananModel>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    // Berhasil disimpan
//                    Toast.makeText(fitur_catatanku.this, "Catatan Makanan Berhasil Disimpan", Toast.LENGTH_SHORT).show();

                    // Menutup loading dialog dengan penundaan
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadingDialog.dismiss();

                            // Tampilkan dialog sukses setelah penutupan dialog loading
                            showSuccessDialog();

                            getCatatanMakananDaily();

                            myDialog.dismiss();
                        }
                    }, DELAY_MILLIS);

                } else {
                    showFailedDialog();
                    loadingDialog.dismiss();
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
                loadingDialog.dismiss();
                myDialog.dismiss();
            }
        });
    }

// ...


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
        Call<ApiResponse<List<MakananModel>>> call = apiService.getMakanan();

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

}
