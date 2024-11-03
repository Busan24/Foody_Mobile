package com.example.foody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import android.widget.ScrollView;
import android.widget.ProgressBar;
import android.widget.LinearLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FiturProfil extends AdsActivity {

    Dialog myDialog;
    private ViewSwitcher summarySwitcher;
    private ScrollView profilScrollview;
    private LinearLayout halamanSummary;
    private ArrayAdapter<String> aktivitasAdapter;
    private Button btnAccount;
    private Button btnSummary;
    private int btnAccountTextColor;
    private int btnSummaryTextColor;
    BottomNavigationView bottomNavigationView;

    private TextView logoutProfil;

    private TextView namaUserTextView, emailUserTextView;
    private TextView meanBmiTextView, beratTextView, tinggiTextView, lemakTextView;
    private TextView karbohidratTextView, proteinTextView, garamTextView, gulaTextView;
    private TextView batasKarboTextView, batasProteinTextView, batasLemakTextView, batasGulaTextView, batasGaramTextView, kebutuhanKaloriTextView;
    private TextView aktivitasTextView;
    private TextView namaAkun, emailAkun, dateAkun, genderAkun;
    private TextView editName, editEmail, editUsername, editDate;
    private EditText epNama, epEmail, epUsername, epDate;
    private Spinner aktivitasSpinner;
    private TextView kalori_harian, persentase_kalori;
//    private TextView progres_kebutuhan_kalori;
    private ProgressBar barKalori;
    private static final int PICK_IMAGE_REQUEST = 1;

    private ShapeableImageView fotoProfil;

    private ImageView foto_profil;

    private List<String> aktivitasList;
    private List<String> jenisKelaminList;
    private Spinner jenisKelaminSpinner;
    private ArrayAdapter<String> jenisKelaminAdapter;
    private String authToken;

    private LinearLayout logoutButton;

    private Button  joinPremium;
    private Button  perpanjangPremium;
    private TextView premiumSelesai;
    private ImageView iconPremium;

    private LinearLayout resetPassword;
    private LinearLayout daftarTransaksi;
    private LinearLayout reportPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_fitur_profil, findViewById(R.id.content_frame));
//        setContentView(R.layout.activity_fitur_profil);
        myDialog = new Dialog(this);


//        Button btnEpProfile = findViewById(R.id.btn_edit_profile);
        LinearLayout btnEpProfile = findViewById(R.id.profile);
        summarySwitcher = findViewById(R.id.summary_switcher);
        profilScrollview = findViewById(R.id.profil_scrollview);
        halamanSummary = findViewById(R.id.halaman_summary);
        btnAccount = findViewById(R.id.btn_Account);
        btnSummary = findViewById(R.id.btn_summary);

        namaUserTextView = findViewById(R.id.nama_user);
        emailUserTextView = findViewById(R.id.email_user);
        meanBmiTextView = findViewById(R.id.hasil_mean_bmi);
        beratTextView = findViewById(R.id.hasil_berat);
        tinggiTextView = findViewById(R.id.hasil_tinggi);
        lemakTextView = findViewById(R.id.hasil_lemak);
        karbohidratTextView = findViewById(R.id.hasil_karbohidrat);
        proteinTextView = findViewById(R.id.hasil_protein);
        garamTextView = findViewById(R.id.hasil_garam);
        gulaTextView = findViewById(R.id.hasil_gula);
        batasKarboTextView = findViewById(R.id.hasil_bataskarbo);
        batasProteinTextView = findViewById(R.id.hasil_batasprotein);
        batasLemakTextView = findViewById(R.id.hasil_bataslemak);
        kebutuhanKaloriTextView = findViewById(R.id.hasil_kebutuhankalori);
        aktivitasTextView = findViewById(R.id.hasil_aktivitas);
        fotoProfil = findViewById(R.id.foto_profil);

        kalori_harian = findViewById(R.id.kalori_harian);
        persentase_kalori = findViewById(R.id.persentase_kalori);
//        progres_kebutuhan_kalori = findViewById(R.id.progres_kebutuhan_kalori);
        barKalori = findViewById(R.id.bar_kalori);

        batasGulaTextView = findViewById(R.id.hasil_batasgula);
        batasGaramTextView = findViewById(R.id.hasil_batasgaram);


        foto_profil = findViewById(R.id.foto_profil);

        // edit_profile
        editName = findViewById(R.id.ep_nama);
        editDate = findViewById(R.id.ep_date);

        authToken = getAuthToken();

        iconPremium = findViewById(R.id.icon_premium);
        logoutButton = findViewById(R.id.logoutMenu);

        joinPremium = findViewById(R.id.join_premium);
        perpanjangPremium = findViewById(R.id.perpanjang_premium);
        premiumSelesai = findViewById(R.id.premium_selesai);

        resetPassword = findViewById(R.id.ganti_password);
        daftarTransaksi =  findViewById(R.id.transksi);
        reportPdf =  findViewById(R.id.report_pdf);

        if (!getPremiumStatus()) {
            iconPremium.setVisibility(View.GONE);
        }

        joinPremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FiturProfil.this, PremiumActivity.class);
                startActivity(intent);
            }
        });

        perpanjangPremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FiturProfil.this, PremiumActivity.class);
                startActivity(intent);
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInterstitialAdAndNavigate(ResetPasswordActivity.class);
//                Intent intent = new Intent(FiturProfil.this, ResetPasswordActivity.class);
//                startActivity(intent);
            }
        });

        daftarTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInterstitialAdAndNavigate(DaftarTransaksiActivity.class);
//                Intent intent = new Intent(FiturProfil.this, DaftarTransaksiActivity.class);
//                startActivity(intent);
            }
        });

        reportPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!getPremiumStatus()){
                    showPremiumDialog();
                    return;
                }
                Intent intent = new Intent(FiturProfil.this, ReportPdfActivity.class);
                startActivity(intent);
            }
        });

        Button btnFoto = findViewById(R.id.btn_foto);
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), PICK_IMAGE_REQUEST);
            }
        });

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<UserProfile> call = apiService.getUserProfile("Bearer " + authToken);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful()) {
                    UserProfile userProfile = response.body();
                    UserData userData = userProfile.getData();
                    SummaryData summaryData = userProfile.getSummary();

                    DecimalFormat decimalFormat = new DecimalFormat("#.#");

                    // Tampilkan data ke TextView
                    namaUserTextView.setText(userData.getName());
                    emailUserTextView.setText(userData.getEmail());
                    // Sesuaikan dengan atribut UserData dan SummaryData yang lain
                    meanBmiTextView.setText(decimalFormat.format(summaryData.getRata_rata_bmi()));;
                    beratTextView.setText(String.valueOf(userData.getBerat_badan()));
                    tinggiTextView.setText(String.valueOf(userData.getTinggi_badan()));

                    lemakTextView.setText(decimalFormat.format(summaryData.getTotal_lemak()));
                    karbohidratTextView.setText(decimalFormat.format(summaryData.getTotal_karbohidrat()));
                    proteinTextView.setText(decimalFormat.format(summaryData.getTotal_protein()));
                    garamTextView.setText(decimalFormat.format(summaryData.getTotal_garam()));
                    gulaTextView.setText(decimalFormat.format(summaryData.getTotal_gula()));

                    batasKarboTextView.setText(decimalFormat.format(summaryData.getBatas_karbohidrat()));
                    batasProteinTextView.setText(decimalFormat.format(summaryData.getBatas_protein()));
                    batasLemakTextView.setText(decimalFormat.format(summaryData.getBatas_lemak()));
                    kebutuhanKaloriTextView.setText(decimalFormat.format(summaryData.getKebutuhan_kalori()));
                    aktivitasTextView.setText(String.valueOf(summaryData.getAktivitas()));

                    batasGulaTextView.setText(decimalFormat.format(summaryData.getBatas_gula()));
                    batasGaramTextView.setText(decimalFormat.format(summaryData.getBatas_garam()));

                    kalori_harian.setText(decimalFormat.format(summaryData.getProgres_kalori()));
                    persentase_kalori.setText("Progres: " + decimalFormat.format((summaryData.getProgres_kalori() / summaryData.getKebutuhan_kalori() * 100)) + "%");
//                    progres_kebutuhan_kalori.setText(decimalFormat.format(summaryData.getProgres_kalori()) + "/" + decimalFormat.format(summaryData.getKebutuhan_kalori()) + " kal");
                    barKalori.setProgress((int)((double) summaryData.getProgres_kalori() / summaryData.getKebutuhan_kalori() * 100));

                    premiumSelesai.setText(userData.getPremium_until());

                    tampilkanProfil(userData);
                    // ... (sisanya sesuaikan dengan atribut UserData dan SummaryData)
                } else {
                    // Tangani kesalahan pada respons
                    Toast.makeText(FiturProfil.this, "Gagal mengambil profil. Coba lagi.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                // Tangani kesalahan pada permintaan
                Toast.makeText(FiturProfil.this, "Gagal mengambil profil. Periksa koneksi internet Anda.", Toast.LENGTH_SHORT).show();
                Log.e("Get Profile Error", "Gagal mengambil profil", t);
            }
        });


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Memanggil metode untuk melakukan logout
                logoutUser(authToken);
            }
        });

        // Inisialisasi warna teks
        btnAccountTextColor = ContextCompat.getColor(this, R.color.birucerah); // Warna teks untuk btn_Account
        btnSummaryTextColor = ContextCompat.getColor(this, R.color.birutua); // Warna teks untuk btn_Summary

        btnEpProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil metode showCustomDialog() ketika tombol btn_edit_profile ditekan
//                showCustomDialog();
//                Intent intent = new Intent(FiturProfil.this, PopupEditProfileActivity.class);
//                startActivity(intent);
                showInterstitialAdAndNavigate(PopupEditProfileActivity.class);
            }
        });

        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cek apakah sedang berada di tampilan activity_switch_akun
                switchView();
            }
        });

        btnSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cek apakah sedang berada di tampilan halaman_summary
                switchView();
            }
        });


        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() { // Ganti menjadi setOnItemSelectedListener
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    // Buka halaman HomeFoodyActivity
                    Intent intent = new Intent(FiturProfil.this, HomeFoodyActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                } else if (item.getItemId() == R.id.nav_catatanku) {
                    // Buka halaman activity_fitur_catatanku
                    Intent intent = new Intent(FiturProfil.this, fitur_catatanku.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                } else if (item.getItemId() == R.id.nav_kalkulator) {
                    // Buka halaman KalkulatorBmi
                    Intent intent = new Intent(FiturProfil.this, KalkulatorBmi.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                } else if (item.getItemId() == R.id.nav_makanan) {
                    // Buka halaman FiturMakanan
                    Intent intent = new Intent(FiturProfil.this, FiturMakanan.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                } else if (item.getItemId() == R.id.nav_profil) {
                    // Buka halaman FiturProfil
                    return true;
                }
                return true;
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        showInterstitialAd();
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();

            // Set gambar ke ImageView
            ShapeableImageView fotoProfil = findViewById(R.id.foto_profil);
            fotoProfil.setImageURI(selectedImageUri);

            // Mengirim gambar ke server
            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                uploadImage(bitmap); // Metode untuk mengunggah gambar ke server
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void switchView() {
        showInterstitialAd();
        if (summarySwitcher.getCurrentView() == summarySwitcher.findViewById(R.id.switch_akun)) {
            summarySwitcher.showPrevious();
            btnAccount.setBackgroundColor(Color.TRANSPARENT);
            btnAccount.setTextColor(btnSummaryTextColor);
            btnSummary.setBackgroundResource(R.drawable.pp_oval_birutua);
            btnSummary.setTextColor(btnAccountTextColor);
        } else {
            showBanner();
            summarySwitcher.showNext();
            btnAccount.setBackgroundResource(R.drawable.pp_oval_birutua);
            btnAccount.setTextColor(btnAccountTextColor);
            btnSummary.setBackgroundColor(Color.TRANSPARENT);
            btnSummary.setTextColor(btnSummaryTextColor);
        }
    }

    private void uploadImage(Bitmap bitmap) {
        // Konversi Bitmap menjadi byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();

        // Buat RequestBody dari byte array
        RequestBody requestFile = RequestBody.create(imageBytes, MediaType.parse("multipart/form-data"));

        // Buat MultipartBody.Part untuk file
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("gambar", "profile_image.jpg", requestFile);

        // Kirim gambar ke server menggunakan Retrofit
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<ApiResponse<Void>> call = apiService.updateProfilePicture("Bearer " + authToken, imagePart);
        call.enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                if (response.isSuccessful()) {
                    // Gambar berhasil diupload

                    // Logcat untuk respons sukses
                    Log.d("Response JSON", "Success");

                    // Jika server memberikan data dalam respons, tambahkan logcat untuk menampilkan data tersebut
                    ApiResponse<Void> apiResponse = response.body();
                    if (apiResponse != null) {
                        Log.d("Response JSON", apiResponse.toString());
                    } else {
                        Log.d("Response JSON", "Response body is null");
                    }

                    finish();
                    overridePendingTransition(0,0);
                    startActivity(getIntent());
                    overridePendingTransition(0,0);

                    Toast.makeText(FiturProfil.this, "Gambar berhasil diupload", Toast.LENGTH_SHORT).show();
                } else {
                    // Gagal mengupload gambar
                    try {
                        // Coba ambil error body dari response
                        String errorBody = response.errorBody().string();
                        Log.e("Error Response", errorBody);
                        Toast.makeText(FiturProfil.this, "Gagal mengupload gambar: " + errorBody, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(FiturProfil.this, "Gagal membaca pesan error", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                // Handle kesalahan jaringan atau server
                Toast.makeText(FiturProfil.this, "Terjadi kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void tampilkanProfil(UserData userData) {
        if (userData.getGambar() != null && !userData.getGambar().isEmpty()) {
            // Mendapatkan model dari ShapeAppearanceOverlay di XML
            ShapeAppearanceModel shapeAppearanceModel = fotoProfil.getShapeAppearanceModel();

            // Menggunakan Glide untuk memuat gambar
            Glide.with(this)
                    .load(userData.getGambar())
                    .apply(RequestOptions.circleCropTransform())
                    .into(fotoProfil);

            // Menetapkan ShapeAppearanceModel yang sama ke ImageView
            fotoProfil.setShapeAppearanceModel(shapeAppearanceModel);
        } else {
            // Jika URL gambar kosong atau null, tampilkan gambar default
            fotoProfil.setImageResource(R.drawable.profil_user);
        }
    }

    private String getStringForAktivitas(double aktivitasValue) {
        if (aktivitasValue == 1.200) {
            return "Tidak aktif (tidak berolahraga sama sekali)";
        } else if (aktivitasValue == 1.375) {
            return "Cukup aktif (berolahraga 1-3x seminggu)";
        } else if (aktivitasValue == 1.55) {
            return "Aktif (berolahraga 3-5x seminggu)";
        } else if (aktivitasValue == 1.725) {
            return "Sangat aktif (berolahraga 6-7x seminggu)";
        } else if (aktivitasValue == 1.9) {
            return "Super Aktif (berolahraga 1-2x setiap hari)";
        }else {
            return "Aktivitas"; // Default atau jika aktivitas tidak ada
        }
    }

    // Metode untuk melakukan logout
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
//                    Toast.makeText(FiturProfil.this, "Logout berhasil!", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(FiturProfil.this, hal_awal.class);
//                    startActivity(intent);
//                    finish(); // Menutup activity saat ini agar tidak dapat kembali ke FiturProfil melalui tombol back
                } else {
                    // Tangani kesalahan pada respons
//                    Toast.makeText(FiturProfil.this, "Logout gagal. Coba lagi.", Toast.LENGTH_SHORT).show();
                }
                SharedPreferences sharedPreferences = getSharedPreferences("login_status", MODE_PRIVATE);

                // Menghapus preferensi login
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("is_logged_in");
                editor.apply();
                // Jika logout berhasil, pindah ke halaman hal_awal
                Toast.makeText(FiturProfil.this, "Logout berhasil!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FiturProfil.this, hal_awal.class);
                startActivity(intent);
                finish(); // Menutup activity saat i
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Tangani kesalahan pada permintaan
//                Toast.makeText(FiturProfil.this, "Logout gagal. Periksa koneksi internet Anda.", Toast.LENGTH_SHORT).show();
//
//                // Tambahkan log untuk melihat kesalahan pada logcat
//                Log.e("Logout Error", "Logout gagal", t);

                SharedPreferences sharedPreferences = getSharedPreferences("login_status", MODE_PRIVATE);

                // Menghapus preferensi login
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("is_logged_in");
                editor.apply();
                // Jika logout berhasil, pindah ke halaman hal_awal
                Toast.makeText(FiturProfil.this, "Logout berhasil!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FiturProfil.this, hal_awal.class);
                startActivity(intent);
                finish(); // Menutup activity saat i
            }

        });
    }

    private void showBanner() {
        boolean premium = getPremiumStatus();

        LinearLayout premiumBanner = findViewById(R.id.banner_premium);
        LinearLayout nonpremiumBanner = findViewById(R.id.banner_nonpremium);

        if (premium) {
            nonpremiumBanner.setVisibility(View.GONE);
        }
        else {
            premiumBanner.setVisibility(View.GONE);
        }

    }

    private String getAuthToken() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("auth_token", MODE_PRIVATE);
        String authToken = sharedPreferences.getString("token", "");
        Log.d("AuthToken", "Token: " + authToken); // Tambahkan log ini
        return authToken;
    }
}