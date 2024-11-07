package com.orion.foody;

import androidx.annotation.NonNull;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.ActivityNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import android.os.Environment;
import android.provider.Settings;

import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFoodyActivity extends AdsActivity {
    private static final int REQUEST_CODE_UNKNOWN_APP = 1234;

    BottomNavigationView bottomNavigationView;

    ViewSwitcher switcher;
    private TextView namaAkun;
    private String authToken;

    private ShapeableImageView fotoProfil;

    private ImageView gotoPremium;

    private WebView webView;
    private TextView karbohidratTextView, proteinTextView, garamTextView, gulaTextView, lemakTextView;
    private TextView batasKarbohidrat, batasProtein, batasGula, batasLemak, batasGaram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.home_foody, findViewById(R.id.content_frame));
//        setContentView(R.layout.home_foody);


        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);


        gotoPremium = findViewById(R.id.goto_premium);

        ImageView gotoPremium = findViewById(R.id.goto_premium);

        //      BAGIAN REKOMENDASI RPODUK AKHIR

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() { // Ganti menjadi setOnItemSelectedListener
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    // Buka halaman HomeFoodyActivity
                    return true;
                } else if (item.getItemId() == R.id.nav_catatanku) {
                    // Buka halaman activity_fitur_catatanku
                    Intent intent = new Intent(HomeFoodyActivity.this, fitur_catatanku.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                } else if (item.getItemId() == R.id.nav_kalkulator) {
                    // Buka halaman KalkulatorBmi
                    Intent intent = new Intent(HomeFoodyActivity.this, KalkulatorBmi.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                } else if (item.getItemId() == R.id.nav_makanan) {
                    // Buka halaman FiturMakanan
                    Intent intent = new Intent(HomeFoodyActivity.this, FiturMakanan.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                } else if (item.getItemId() == R.id.nav_profil) {
                    // Buka halaman FiturProfil
                    Intent intent = new Intent(HomeFoodyActivity.this, FiturProfil.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
                return true;
            }

        });

       getAppUpdate();

        switcher = findViewById(R.id.switcher_dharian); // Inisialisasi ViewSwitcher

        authToken = getAuthToken();

        getDataDialyCatatan();
        getCatatanMakananDaily();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_UNKNOWN_APP) {
            if (resultCode == RESULT_OK) {
                // Izin sudah diberikan, sekarang bisa melanjutkan proses update
                // Panggil kembali performAppUpdate atau lanjutkan proses yang tertunda
            } else {
                // Izin tidak diberikan, tampilkan pesan peringatan ke pengguna
                Toast.makeText(this, "Izin untuk menginstal dari sumber tidak dikenal dibutuhkan.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getDataDialyCatatan(){
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        String authToken = "Bearer " + getAuthToken();
        // Melakukan permintaan profil pengguna dengan menyertakan token
        Call<UserProfile> call = apiService.getUserProfile("Bearer " + authToken);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful()) {
                    UserProfile userProfile = response.body();
                    UserData userData = userProfile.getData();
                    SummaryData summaryData = userProfile.getSummary();
//                    tampilkanProfil(userData);

                    Log.d("Verification Status", "User isVerified: " + userData.isVerified());
                    if (!userData.isVerified()) {
                        Intent intent = new Intent(HomeFoodyActivity.this, VerifikasiOtp.class);
                        startActivity(intent);
                        finish();
                    }

                    // Save premium status
                    savePremiumStatus(userData.isPremium());
                    if (userData.isPremium()) {
                        gotoPremium.setImageResource(R.drawable.icon_foody_premium);
                    }
//
                    saveVerifiedStatus(userData.isVerified());

                    DecimalFormat decimalFormat = new DecimalFormat("#.#");

                    namaAkun = findViewById(R.id.username_home);

                    lemakTextView = findViewById(R.id.hasil_lemak);
                    karbohidratTextView = findViewById(R.id.hasil_carbo);
                    proteinTextView = findViewById(R.id.hasil_protein);
                    garamTextView = findViewById(R.id.hasil_garam);
                    gulaTextView = findViewById(R.id.hasil_gula);

                    batasKarbohidrat = findViewById(R.id.batas_karbohidrat);
                    batasProtein = findViewById(R.id.batas_protein);
                    batasGula = findViewById(R.id.batas_gula);
                    batasLemak = findViewById(R.id.batas_lemak);
                    batasGaram = findViewById(R.id.batas_garam);

                    namaAkun.setText(userData.getName());

                    lemakTextView.setText(decimalFormat.format(summaryData.getDaily_lemak()));
                    karbohidratTextView.setText(decimalFormat.format(summaryData.getDaily_karbohidrat()));
                    proteinTextView.setText(decimalFormat.format(summaryData.getDaily_protein()));
                    garamTextView.setText(decimalFormat.format(summaryData.getDaily_garam()));
                    gulaTextView.setText(decimalFormat.format(summaryData.getDaily_gula()));

                    batasKarbohidrat.setText(decimalFormat.format(summaryData.getBatas_karbohidrat()));
                    batasProtein.setText(decimalFormat.format(summaryData.getBatas_protein()));
                    batasLemak.setText(decimalFormat.format(summaryData.getBatas_lemak()));
                    batasGula.setText(decimalFormat.format(summaryData.getBatas_gula()));
                    batasGaram.setText(decimalFormat.format(summaryData.getBatas_garam()));

                    TextView hpKarbohidrat = findViewById(R.id.hasil_carbo_persen);
                    TextView hpProtein = findViewById(R.id.hasil_protein_persen);
                    TextView hpGula = findViewById(R.id.hasil_gula_persen);
                    TextView hpLemak = findViewById(R.id.hasil_lemak_persen);
                    TextView hpGaram = findViewById(R.id.hasil_garam_persen);

                    ProgressBar progressBarKarbohidrat = findViewById(R.id.bar_carbo);
                    ProgressBar progressBarProtein = findViewById(R.id.bar_protein);
                    ProgressBar progressBarGula = findViewById(R.id.bar_gula);
                    ProgressBar progressBarLemak = findViewById(R.id.bar_lemak);
                    ProgressBar progressBarGaram = findViewById(R.id.bar_garam);

                    double batasKarbo = summaryData.getBatas_karbohidrat();
                    double batasProtein = summaryData.getBatas_protein();
                    double batasGula = summaryData.getBatas_gula();
                    double batasLemak = summaryData.getBatas_lemak();
                    double batasGaram = summaryData.getBatas_garam();

                    int hasilKarboPersen = (int) ((double) summaryData.getDaily_karbohidrat() / batasKarbo * 100);
                    int hasilProteinPersen = (int) ((double) summaryData.getDaily_protein() / batasProtein * 100);
                    int hasilGulaPersen = (int) ((double) summaryData.getDaily_gula() / batasGula * 100);
                    int hasilLemakPersen = (int) ((double) summaryData.getDaily_lemak() / batasLemak * 100);
                    int hasilGaramPersen = (int) ((double) summaryData.getDaily_garam() / batasGaram * 100);

                    // Baca hasil ProgressBar
                    progressBarKarbohidrat.setProgress(hasilKarboPersen);
                    progressBarProtein.setProgress(hasilProteinPersen);
                    progressBarGula.setProgress(hasilGulaPersen);
                    progressBarGaram.setProgress(hasilGaramPersen);
                    progressBarLemak.setProgress(hasilLemakPersen);

                    hpKarbohidrat.setText(String.valueOf(hasilKarboPersen));
                    hpProtein.setText(String.valueOf(hasilProteinPersen));
                    hpGula.setText(String.valueOf(hasilGulaPersen));
                    hpLemak.setText(String.valueOf(hasilLemakPersen));
                    hpGaram.setText(String.valueOf(hasilGaramPersen));
                    // ... (sisanya sesuaikan dengan atribut UserData dan SummaryData)
                } else {
                    // Tangani kesalahan pada respons
                    Toast.makeText(HomeFoodyActivity.this, "Gagal mengambil profil. Coba lagi.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                // Tangani kesalahan pada permintaan
                Toast.makeText(HomeFoodyActivity.this, "Gagal mengambil profil. Periksa koneksi internet Anda.", Toast.LENGTH_SHORT).show();
                Log.e("Get Profile Error", "Gagal mengambil profil", t);
            }
        });
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
//                    showChartInWebView(response.body().getMessage());
                    ImageView imageView = findViewById(R.id.imageView);
                    Glide.with(HomeFoodyActivity.this)
                            .load(response.body().getMessage())
                            .into(imageView);
//                    showChartInWebView(link);
                } else {
                    // Handle kesalahan respons
                    Toast.makeText(HomeFoodyActivity.this, "Gagal mendapatkan catatan makanan harian", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<CatatanMakananModel>>> call, Throwable t) {
                // Handle kesalahan jaringan
                Toast.makeText(HomeFoodyActivity.this, "Terjadi kesalahan jaringan", Toast.LENGTH_SHORT).show();
                Log.e("RetrofitError", "Error: " + t.getMessage(), t);
            }
        });
    }

    private void getAppUpdate() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        String authToken = "Bearer " + getAuthToken();

        Call<ApiResponse<NewReleaseModel>> call = apiService.getLatestVersion("Bearer " + authToken);
        call.enqueue(new Callback<ApiResponse<NewReleaseModel>>() {
            @Override
            public void onResponse(Call<ApiResponse<NewReleaseModel>> call, Response<ApiResponse<NewReleaseModel>> response) {
                if (response.isSuccessful()) {
                    NewReleaseModel newReleaseModel = response.body().getData();

                    try {
                        // Mendapatkan versi aplikasi
                        PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                        String versionName = packageInfo.versionName;

                        if (isNewerVersion(newReleaseModel.getVersion(), versionName)) {
                            showUpdateDialog(newReleaseModel);
                        }

                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(Call<ApiResponse<NewReleaseModel>> call, Throwable t) {
                // Handle kesalahan jaringan
                Toast.makeText(HomeFoodyActivity.this, "Terjadi kesalahan jaringan", Toast.LENGTH_SHORT).show();
                Log.e("RetrofitError", "Error: " + t.getMessage(), t);
            }
        });
    }

    public void showUpdateDialog(NewReleaseModel release) {
        Dialog updateDialog = new Dialog(HomeFoodyActivity.this);
        updateDialog.setContentView(R.layout.popup_update);

        TextView versi = updateDialog.findViewById(R.id.version);
        TextView changelog = updateDialog.findViewById(R.id.chanagelog);
        TextView skip = updateDialog.findViewById(R.id.skip);
        Button update_button = updateDialog.findViewById(R.id.btn_update);

        versi.append(" " + release.getVersion());

        updateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAppUpdate(release.getUrl(), release.getVersion());
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tutup dialog sukses
                updateDialog.dismiss();
            }
        });

        // Tampilkan dialog berhasil
        updateDialog.show();
    }

    private void performAppUpdate(String url, String version) {
        File apkFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "Foody_v" + version + ".apk");

        if (apkFile.exists()) {
            // If the APK file already exists, install it directly
            Uri apkUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", apkFile);
            installApk(apkUri);
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean canInstall = getPackageManager().canRequestPackageInstalls();
            if (!canInstall) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
                        .setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_CODE_UNKNOWN_APP);
                return; // Exit the method to wait for the user to grant permission
            }
        }

        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle("Mengunduh Pembaruan");
        request.setDescription("Mengunduh versi terbaru aplikasi");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "Foody_v" + version + ".apk");

        long downloadId = downloadManager.enqueue(request);
        Toast.makeText(HomeFoodyActivity.this, "Mendownload pembaruan...", Toast.LENGTH_SHORT).show();

        // BroadcastReceiver to detect when the download is complete
        BroadcastReceiver onComplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (downloadId == id) {
                    Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider",
                            new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "Foody_v" + version + ".apk"));
                    installApk(apkUri);
                }
            }
        };
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    // Metode untuk menginstal APK menggunakan FileProvider
    private void installApk(Uri apkUri) {
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        installIntent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        installIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(installIntent);
        } catch (ActivityNotFoundException e) {
            Log.e("InstallApk", "Error starting install activity", e);
        }
    }



    private boolean isNewerVersion(String latestVersion, String currentVersion) {
        String[] latestParts = latestVersion.split("\\.");
        String[] currentParts = currentVersion.split("\\.");

        for (int i = 0; i < latestParts.length; i++) {
            int latest = Integer.parseInt(latestParts[i]);
            int current = Integer.parseInt(currentParts[i]);

            if (latest > current) {
                return true;
            } else if (latest < current) {
                return false;
            }
        }
        return false;
    }

    private void showChartInWebView(String chartUrl) {
        // Load data chart ke dalam WebView
        webView.loadUrl(chartUrl);
    }

    private String getAuthToken() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("auth_token", MODE_PRIVATE);
        String authToken = sharedPreferences.getString("token", "");
        Log.d("AuthToken", "Token: " + authToken); // Tambahkan log ini
        return authToken;
    }

    private void savePremiumStatus(boolean premium) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("premium_status", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_premium", premium);
        editor.apply();
    }

    private void saveVerifiedStatus(boolean verified) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("verified_status", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_verified", verified);
        editor.apply();
    }
}
