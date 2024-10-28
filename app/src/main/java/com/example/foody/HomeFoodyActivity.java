package com.example.foody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.shape.ShapeAppearanceModel;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFoodyActivity extends AppCompatActivity {
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
        setContentView(R.layout.home_foody);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

//        fotoProfil = findViewById(R.id.profilhome);
//
//        ImageView profilHome = findViewById(R.id.)profilhome;

        gotoPremium = findViewById(R.id.goto_premium);

        ImageView gotoPremium = findViewById(R.id.goto_premium);

//        webView = findViewById(R.id.webView);
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//
//        webView.setBackgroundColor(0);
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setBuiltInZoomControls(false);
//        webView.getSettings().setDisplayZoomControls(false);
//
//        webView.post(new Runnable() {
//            @Override
//            public void run() {
//                int width = webView.getWidth();
//                ViewGroup.LayoutParams params = webView.getLayoutParams();
//                params.height = width;
//                webView.setLayoutParams(params);
//            }
//        });


//        profilHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(HomeFoodyActivity.this, FiturProfil.class);
//                startActivity(intent);
//            }
//        });


        gotoPremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeFoodyActivity.this, PremiumActivity.class);
                startActivity(intent);
            }
        });

//      BAGIAN REKOMENDASI RPODUK AWAL

//        TextView viewAllTextView = findViewById(R.id.viewall_produk);
//
//        viewAllTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(HomeFoodyActivity.this, RekomendasiProduk.class);
//                startActivity(intent);
//            }
//        });
//
//        ImageView arrowAllProduk = findViewById(R.id.vall_produk);
//
//        arrowAllProduk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(HomeFoodyActivity.this, RekomendasiProduk.class);
//                startActivity(intent);
//            }
//        });
//
//
//        // Membuat dialog kustom
//        final Dialog customDialog = new Dialog(this);
//        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        customDialog.setContentView(R.layout.activity_dialog_rekommakanan);
//
//
//        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//
//        // Mendapatkan referensi elemen "see_details" dan "arrow_rekom"
//        TextView seeDetails = findViewById(R.id.see_details);
//        ImageView arrowRekom = findViewById(R.id.arrow_rekom);
//
//        // Menambahkan OnClickListener pada "see_details"
//        seeDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Tampilkan dialog kustom ketika "see_details" diklik
//
//                // Mendapatkan posisi elemen "see_details" pada layar
//                int[] location = new int[2];
//                v.getLocationOnScreen(location);
//
//                // Menentukan posisi vertikal dialog berdasarkan elemen "see_details"
//                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//                layoutParams.copyFrom(customDialog.getWindow().getAttributes());
//                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; // Mengatur lebar dialog menjadi full layar
//                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                layoutParams.gravity = Gravity.TOP | Gravity.START;
//                layoutParams.x = location[0]; // Mengikuti posisi horizontal elemen "see_details"
//                layoutParams.y = location[1] + v.getHeight(); // Menampilkan dialog di bawah elemen "see_details"
//
//                customDialog.getWindow().setAttributes(layoutParams);
//                customDialog.show();
//            }
//        });
//        arrowRekom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Tampilkan dialog kustom ketika "arrow_rekom" diklik
//
//                // Mendapatkan posisi elemen "arrow_rekom" pada layar
//                int[] location = new int[2];
//                v.getLocationOnScreen(location);
//
//                // Menentukan posisi vertikal dialog berdasarkan elemen "arrow_rekom"
//                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//                layoutParams.copyFrom(customDialog.getWindow().getAttributes());
//                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; // Mengatur lebar dialog menjadi full layar
//                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                layoutParams.gravity = Gravity.TOP | Gravity.START;
//                layoutParams.x = location[0]; // Mengikuti posisi horizontal elemen "arrow_rekom"
//                layoutParams.y = location[1] + v.getHeight(); // Menampilkan dialog di bawah elemen "arrow_rekom"
//
//                customDialog.getWindow().setAttributes(layoutParams);
//                customDialog.show();
//
//
//            }
//        });
//
//        Button btnRekomDialog = customDialog.findViewById(R.id.btn_rekom);
//        btnRekomDialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Intent untuk membuka link pada browser
//                String shopeeLink = "https://shopee.co.id/Granola-400-gr-LESS-SWEET-Nutriology-Sereal-Sehat-Makanan-Diet-Crunchy-Lengkap-Murah-Bergizi-Alami-Natural-High-Quality-i.61035441.22545782983?sp_atk=2124f05e-9378-425e-be88-0e3de518fce4&xptdk=2124f05e-9378-425e-be88-0e3de518fce4";
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(shopeeLink));
//                startActivity(intent);
//
//                // Tutup dialog setelah membuka link
//                customDialog.dismiss();
//            }
//        });
//
//
//        final Dialog dialogProdukDua = new Dialog(this);
//        dialogProdukDua.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialogProdukDua.setContentView(R.layout.activity_dialogr_rekomdua);
//
//        dialogProdukDua.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//
//        // Mendapatkan referensi elemen "see_details" dan "arrow_rekom"
//        TextView seeDetailsProdukDua = findViewById(R.id.lihat_produkdua);
//        ImageView arrowRekomProdukDua = findViewById(R.id.arw_produkdua);
//
//        // Menambahkan OnClickListener pada "see_details"
//        seeDetailsProdukDua.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Tampilkan dialog kustom ketika "see_details" diklik
//
//                // Mendapatkan posisi elemen "see_details" pada layar
//                int[] location = new int[2];
//                v.getLocationOnScreen(location);
//
//                // Menentukan posisi vertikal dialog berdasarkan elemen "see_details"
//                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//                layoutParams.copyFrom(dialogProdukDua.getWindow().getAttributes());
//                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; // Mengatur lebar dialog menjadi full layar
//                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                layoutParams.gravity = Gravity.TOP | Gravity.START;
//                layoutParams.x = location[0]; // Mengikuti posisi horizontal elemen "see_details"
//                layoutParams.y = location[1] + v.getHeight(); // Menampilkan dialog di bawah elemen "see_details"
//
//                dialogProdukDua.getWindow().setAttributes(layoutParams);
//                dialogProdukDua.show();
//            }
//        });
//
//        arrowRekomProdukDua.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Tampilkan dialog kustom ketika "arrow_rekom" diklik
//
//                // Mendapatkan posisi elemen "arrow_rekom" pada layar
//                int[] location = new int[2];
//                v.getLocationOnScreen(location);
//
//                // Menentukan posisi vertikal dialog berdasarkan elemen "arrow_rekom"
//                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//                layoutParams.copyFrom(dialogProdukDua.getWindow().getAttributes());
//                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; // Mengatur lebar dialog menjadi full layar
//                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                layoutParams.gravity = Gravity.TOP | Gravity.START;
//                layoutParams.x = location[0]; // Mengikuti posisi horizontal elemen "arrow_rekom"
//                layoutParams.y = location[1] + v.getHeight(); // Menampilkan dialog di bawah elemen "arrow_rekom"
//
//                dialogProdukDua.getWindow().setAttributes(layoutParams);
//                dialogProdukDua.show();
//
//
//            }
//        });
//
//        Button btnRekomDua = dialogProdukDua.findViewById(R.id.btn_rekomdua);
//        btnRekomDua.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Intent untuk membuka link pada browser
//                String shopeeLink = "https://www.tokopedia.com/leanlab/lean-lab-selai-matcha-250gr-peanut-butter-powder-selai-rendah-kalori?extParam=ivf%3Dtrue&src=topads";
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(shopeeLink));
//                startActivity(intent);
//
//                // Tutup dialog setelah membuka link
//                dialogProdukDua.dismiss();
//            }
//        });
//
//
//        final Dialog dialogProdukTiga = new Dialog(this);
//        dialogProdukTiga.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialogProdukTiga.setContentView(R.layout.activity_dialog_rekomtiga);
//
//        dialogProdukTiga.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//
//        // Mendapatkan referensi elemen "see_details" dan "arrow_rekom"
//        TextView seeDetailsProdukTiga = findViewById(R.id.lihat_produktiga);
//        ImageView arrowRekomProdukTiga = findViewById(R.id.arw_produktiga);
//
//        // Menambahkan OnClickListener pada "see_details"
//        seeDetailsProdukTiga.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Tampilkan dialog kustom ketika "see_details" diklik
//
//                // Mendapatkan posisi elemen "see_details" pada layar
//                int[] location = new int[2];
//                v.getLocationOnScreen(location);
//
//                // Menentukan posisi vertikal dialog berdasarkan elemen "see_details"
//                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//                layoutParams.copyFrom(dialogProdukTiga.getWindow().getAttributes());
//                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; // Mengatur lebar dialog menjadi full layar
//                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                layoutParams.gravity = Gravity.TOP | Gravity.START;
//                layoutParams.x = location[0]; // Mengikuti posisi horizontal elemen "see_details"
//                layoutParams.y = location[1] + v.getHeight(); // Menampilkan dialog di bawah elemen "see_details"
//
//                dialogProdukTiga.getWindow().setAttributes(layoutParams);
//                dialogProdukTiga.show();
//            }
//        });
//
//        arrowRekomProdukTiga.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Tampilkan dialog kustom ketika "arrow_rekom" diklik
//
//                // Mendapatkan posisi elemen "arrow_rekom" pada layar
//                int[] location = new int[2];
//                v.getLocationOnScreen(location);
//
//                // Menentukan posisi vertikal dialog berdasarkan elemen "arrow_rekom"
//                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//                layoutParams.copyFrom(dialogProdukTiga.getWindow().getAttributes());
//                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; // Mengatur lebar dialog menjadi full layar
//                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                layoutParams.gravity = Gravity.TOP | Gravity.START;
//                layoutParams.x = location[0]; // Mengikuti posisi horizontal elemen "arrow_rekom"
//                layoutParams.y = location[1] + v.getHeight(); // Menampilkan dialog di bawah elemen "arrow_rekom"
//
//                dialogProdukTiga.getWindow().setAttributes(layoutParams);
//                dialogProdukTiga.show();
//
//
//            }
//        });
//
//        Button btnRekomTiga = dialogProdukTiga.findViewById(R.id.btn_rekomtiga);
//        btnRekomTiga.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Intent untuk membuka link pada browser
//                String shopeeLink = "https://tokopedia.link/7Xa2HOkCVEb";
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(shopeeLink));
//                startActivity(intent);
//
//                // Tutup dialog setelah membuka link
//                dialogProdukTiga.dismiss();
//            }
//        });
//
//
//        final Dialog dialogProdukEmpat = new Dialog(this);
//        dialogProdukEmpat.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialogProdukEmpat.setContentView(R.layout.activity_dialog_rekomempat);
//
//        dialogProdukEmpat.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//
//        // Mendapatkan referensi elemen "see_details" dan "arrow_rekom"
//        TextView seeDetailsProdukEmpat = findViewById(R.id.lihat_produkempat);
//        ImageView arrowRekomProdukEmpat = findViewById(R.id.arw_produkempat);
//
//        // Menambahkan OnClickListener pada "see_details"
//        seeDetailsProdukEmpat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Tampilkan dialog kustom ketika "see_details" diklik
//
//                // Mendapatkan posisi elemen "see_details" pada layar
//                int[] location = new int[2];
//                v.getLocationOnScreen(location);
//
//                // Menentukan posisi vertikal dialog berdasarkan elemen "see_details"
//                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//                layoutParams.copyFrom(dialogProdukEmpat.getWindow().getAttributes());
//                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; // Mengatur lebar dialog menjadi full layar
//                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                layoutParams.gravity = Gravity.TOP | Gravity.START;
//                layoutParams.x = location[0]; // Mengikuti posisi horizontal elemen "see_details"
//                layoutParams.y = location[1] + v.getHeight(); // Menampilkan dialog di bawah elemen "see_details"
//
//                dialogProdukEmpat.getWindow().setAttributes(layoutParams);
//                dialogProdukEmpat.show();
//            }
//        });
//
//        arrowRekomProdukEmpat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Tampilkan dialog kustom ketika "arrow_rekom" diklik
//
//                // Mendapatkan posisi elemen "arrow_rekom" pada layar
//                int[] location = new int[2];
//                v.getLocationOnScreen(location);
//
//                // Menentukan posisi vertikal dialog berdasarkan elemen "arrow_rekom"
//                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//                layoutParams.copyFrom(dialogProdukEmpat.getWindow().getAttributes());
//                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; // Mengatur lebar dialog menjadi full layar
//                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                layoutParams.gravity = Gravity.TOP | Gravity.START;
//                layoutParams.x = location[0]; // Mengikuti posisi horizontal elemen "arrow_rekom"
//                layoutParams.y = location[1] + v.getHeight(); // Menampilkan dialog di bawah elemen "arrow_rekom"
//
//                dialogProdukEmpat.getWindow().setAttributes(layoutParams);
//                dialogProdukEmpat.show();
//
//
//            }
//        });
//
//        Button btnRekomEmpat = dialogProdukEmpat.findViewById(R.id.btn_rekomEmpat);
//        btnRekomEmpat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Intent untuk membuka link pada browser
//                String shopeeLink = "https://shopee.co.id/Proteina-LX-250-gr-Protein-Nabati-Vegetarian-Healthy-Food-i.1001474331.22742464977?sp_atk=c9f90000-cbd1-4915-83c6-0b2262b23f30&xptdk=c9f90000-cbd1-4915-83c6-0b2262b23f30";
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(shopeeLink));
//                startActivity(intent);
//
//                // Tutup dialog setelah membuka link
//                dialogProdukEmpat.dismiss();
//            }
//        });

        // Menambahkan OnClickListener pada "arrow_rekom"



//        // Menutup dialog saat tombol "Close" pada dialog ditekan
//        Button closeButton = customDialog.findViewById(R.id.btn_close);
//        if (closeButton != null) {
//            closeButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    customDialog.dismiss();
//                }
//            });
//        }

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
                } else if (item.getItemId() == R.id.nav_kalkulator) {
                    // Buka halaman KalkulatorBmi
                    Intent intent = new Intent(HomeFoodyActivity.this, KalkulatorBmi.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.nav_makanan) {
                    // Buka halaman FiturMakanan
                    Intent intent = new Intent(HomeFoodyActivity.this, FiturMakanan.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.nav_profil) {
                    // Buka halaman FiturProfil
                    Intent intent = new Intent(HomeFoodyActivity.this, FiturProfil.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
                return true;
            }

        });

        switcher = findViewById(R.id.switcher_dharian); // Inisialisasi ViewSwitcher

//        Button btnHariSenen = findViewById(R.id.btn_hari_senen);
//        Button btnHariSelasa = findViewById(R.id.btn_hari_selasa);
//        Button btnHariRabu = findViewById(R.id.btn_hari_rabu);
//        Button btnHariKamis = findViewById(R.id.btn_hari_kamis);
//        Button btnHariJumat = findViewById(R.id.btn_hari_jumat);
//        Button btnHariSabtu = findViewById(R.id.btn_hari_sabtu);
//        Button btnHariMinggu = findViewById(R.id.btn_hari_minggu);

//        btnHariSenen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    switcher.showNext();
//                    btnHariSenen.setBackgroundResource(R.drawable.btn_date_home);
//                    btnHariSelasa.setBackgroundResource(R.drawable.btn_bulat_putih);
//                    btnHariRabu.setBackgroundResource(R.drawable.btn_bulat_putih);
//                    btnHariKamis.setBackgroundResource(R.drawable.btn_bulat_putih);
//                    btnHariJumat.setBackgroundResource(R.drawable.btn_bulat_putih);
//                    btnHariSabtu.setBackgroundResource(R.drawable.btn_bulat_putih);
//                    btnHariMinggu.setBackgroundResource(R.drawable.btn_bulat_putih);
//                }
//        });
//
//        btnHariSelasa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    switcher.showNext();
//                    btnHariSelasa.setBackgroundResource(R.drawable.btn_date_home);
//                    btnHariSenen.setBackgroundResource(R.drawable.btn_bulat_putih);
//                    btnHariRabu.setBackgroundResource(R.drawable.btn_bulat_putih);
//                    btnHariKamis.setBackgroundResource(R.drawable.btn_bulat_putih);
//                    btnHariJumat.setBackgroundResource(R.drawable.btn_bulat_putih);
//                    btnHariSabtu.setBackgroundResource(R.drawable.btn_bulat_putih);
//                    btnHariMinggu.setBackgroundResource(R.drawable.btn_bulat_putih);
//                }
//        });
//
//        btnHariRabu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    switcher.showNext();
//                    btnHariSelasa.setBackgroundResource(R.drawable.btn_bulat_putih);
//                    btnHariSenen.setBackgroundResource(R.drawable.btn_bulat_putih);
//                    btnHariRabu.setBackgroundResource(R.drawable.btn_date_home);
//                    btnHariKamis.setBackgroundResource(R.drawable.btn_bulat_putih);
//                    btnHariJumat.setBackgroundResource(R.drawable.btn_bulat_putih);
//                    btnHariSabtu.setBackgroundResource(R.drawable.btn_bulat_putih);
//                    btnHariMinggu.setBackgroundResource(R.drawable.btn_bulat_putih);
//                }
//        });
//
//        btnHariKamis.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switcher.showNext();
//                btnHariSelasa.setBackgroundResource(R.drawable.btn_bulat_putih);
//                btnHariSenen.setBackgroundResource(R.drawable.btn_bulat_putih);
//                btnHariRabu.setBackgroundResource(R.drawable.btn_bulat_putih);
//                btnHariKamis.setBackgroundResource(R.drawable.btn_date_home);
//                btnHariJumat.setBackgroundResource(R.drawable.btn_bulat_putih);
//                btnHariSabtu.setBackgroundResource(R.drawable.btn_bulat_putih);
//                btnHariMinggu.setBackgroundResource(R.drawable.btn_bulat_putih);
//            }
//        });
//
//        btnHariJumat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switcher.showNext();
//                btnHariSelasa.setBackgroundResource(R.drawable.btn_bulat_putih);
//                btnHariSenen.setBackgroundResource(R.drawable.btn_bulat_putih);
//                btnHariRabu.setBackgroundResource(R.drawable.btn_bulat_putih);
//                btnHariKamis.setBackgroundResource(R.drawable.btn_bulat_putih);
//                btnHariJumat.setBackgroundResource(R.drawable.btn_date_home);
//                btnHariSabtu.setBackgroundResource(R.drawable.btn_bulat_putih);
//                btnHariMinggu.setBackgroundResource(R.drawable.btn_bulat_putih);
//            }
//        });
//
//        btnHariSabtu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switcher.showNext();
//                btnHariSelasa.setBackgroundResource(R.drawable.btn_bulat_putih);
//                btnHariSenen.setBackgroundResource(R.drawable.btn_bulat_putih);
//                btnHariRabu.setBackgroundResource(R.drawable.btn_bulat_putih);
//                btnHariKamis.setBackgroundResource(R.drawable.btn_bulat_putih);
//                btnHariJumat.setBackgroundResource(R.drawable.btn_bulat_putih);
//                btnHariSabtu.setBackgroundResource(R.drawable.btn_date_home);
//                btnHariMinggu.setBackgroundResource(R.drawable.btn_bulat_putih);
//            }
//        });
//
//        btnHariMinggu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switcher.showNext();
//                btnHariSelasa.setBackgroundResource(R.drawable.btn_bulat_putih);
//                btnHariSenen.setBackgroundResource(R.drawable.btn_bulat_putih);
//                btnHariRabu.setBackgroundResource(R.drawable.btn_bulat_putih);
//                btnHariKamis.setBackgroundResource(R.drawable.btn_bulat_putih);
//                btnHariJumat.setBackgroundResource(R.drawable.btn_bulat_putih);
//                btnHariSabtu.setBackgroundResource(R.drawable.btn_bulat_putih);
//                btnHariMinggu.setBackgroundResource(R.drawable.btn_date_home);
//            }
//        });


        authToken = getAuthToken();

        getDataDialyCatatan();
        getCatatanMakananDaily();

    }

//    private void tampilkanProfil(UserData userData) {
//        if (userData.getGambar() != null && !userData.getGambar().isEmpty()) {
//            // Mendapatkan model dari ShapeAppearanceOverlay di XML
//            ShapeAppearanceModel shapeAppearanceModel = fotoProfil.getShapeAppearanceModel();
//
//            // Menggunakan Glide untuk memuat gambar
//            Glide.with(this)
//                    .load(userData.getGambar())
//                    .apply(RequestOptions.circleCropTransform())
//                    .into(fotoProfil);
//
//            // Menetapkan ShapeAppearanceModel yang sama ke ImageView
//            fotoProfil.setShapeAppearanceModel(shapeAppearanceModel);
//        } else {
//            // Jika URL gambar kosong atau null, tampilkan gambar default
//            fotoProfil.setImageResource(R.drawable.profil_user);
//        }
//    }

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
                        overridePendingTransition(0, 0);
                    }

                    // Save premium status
                    savePremiumStatus(userData.isPremium());
                    if (userData.isPremium()) {
                        gotoPremium.setImageResource(R.drawable.icon_foody_premium);
                    }
//                    SharedPrefManager.savePremiumStatus(userData.isPremium());

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
}
