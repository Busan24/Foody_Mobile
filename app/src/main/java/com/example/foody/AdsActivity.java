package com.example.foody;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.AdSize;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback;



public class AdsActivity extends AppCompatActivity {
    protected AdView mAdView;
    protected InterstitialAd mInterstitialAd;
    private AppOpenAd appOpenAd;

    private final String BANNER_AD_UNIT_ID_TEST = "ca-app-pub-3940256099942544/9214589741";
    private final String BANNER_AD_UNIT_ID = "ca-app-pub-3047181133940727/6533239510";
    private final String INTERSTISIAL_AD_UNIT_ID_TEST = "ca-app-pub-3940256099942544/1033173712";
    private final String INTERSTISIAL_AD_UNIT_ID = "ca-app-pub-3047181133940727/9072334020";
    private final String OPENING_AD_UNIT_ID_TEST = "ca-app-pub-3940256099942544/9257395921";
    private final String OPENING_AD_UNIT_ID = "ca-app-pub-3047181133940727/8139763403";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inisialisasi AdMob hanya sekali di aplikasi
        MobileAds.initialize(this, initializationStatus -> {});

        // Set layout iklan di activity layout utama
        setContentView(R.layout.activity_with_banner);

        // Set AdView
        mAdView = findViewById(R.id.adView);
//        mAdView.setAdSize(AdSize.BANNER); // Atur ukuran iklan jika belum di XML
//        mAdView.setAdUnitId(BANNER_AD_UNIT_ID_TEST); // Setel adUnitId di sini

        // Muat iklan
        AdRequest adRequest = new AdRequest.Builder().build();

        // Muat iklan
        if (!getPremiumStatus()) {
            mAdView.loadAd(adRequest);
            loadInterstitialAd();
            loadAppOpenAd();
        }

        else {
            mAdView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Tampilkan iklan pembuka jika sudah dimuat
        if (appOpenAd != null) {
            appOpenAd.show(this);
        }
    }

    public void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, INTERSTISIAL_AD_UNIT_ID_TEST, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(InterstitialAd interstitialAd) {
                        // Simpan iklan yang telah dimuat untuka digunakan nanti
                        mInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        mInterstitialAd = null; // Reset jika gagal
                    }
                });
    }

     public void showInterstitialAd() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("premium_status", MODE_PRIVATE);
        boolean premium = sharedPreferences.getBoolean("is_premium", false);
        if (mInterstitialAd != null && !premium) {
            mInterstitialAd.show(this);
            // Setelah ditampilkan, muat ulang iklan agar siap untuk waktu berikutnya
            loadInterstitialAd();
        }
    }

    public void showInterstitialAdAndNavigate(final Class nextActivity) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("premium_status", MODE_PRIVATE);
        boolean premium = sharedPreferences.getBoolean("is_premium", false);

        if (mInterstitialAd != null && !premium) {
            // Atur callback untuk menampilkan aktivitas baru setelah iklan ditutup
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    // Iklan ditutup, sekarang pindah ke aktivitas berikutnya
                    Intent intent = new Intent(AdsActivity.this, nextActivity);
                    startActivity(intent);
                    loadInterstitialAd(); // Muat iklan baru setelah ditampilkan
                }

//                @Override
                public void onAdFailedToShowFullScreenContent(LoadAdError adError) {
                    // Jika gagal menunjukkan iklan, langsung pindah ke aktivitas berikutnya
                    Intent intent = new Intent(AdsActivity.this, nextActivity);
                    startActivity(intent);
                }
            });

            // Tampilkan iklan
            mInterstitialAd.show(this);
        } else {
            // Jika iklan belum dimuat, langsung pindah ke aktivitas berikutnya
            Intent intent = new Intent(AdsActivity.this, nextActivity);
            startActivity(intent);
        }
    }

    private void loadAppOpenAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        AppOpenAd.load(this, OPENING_AD_UNIT_ID_TEST, adRequest,
            new AppOpenAdLoadCallback() {
                @Override
                public void onAdLoaded(AppOpenAd ad) {
                    appOpenAd = ad;
                    appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            appOpenAd = null; // Reset iklan
                            loadAppOpenAd(); // Muat iklan baru setelah ditampilkan
                        }

//                        @Override
//                        public void onAdFailedToShowFullScreenContent(LoadAdError adError) {
//                            appOpenAd = null; // Reset jika gagal ditampilkan
//                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            // Iklan berhasil ditampilkan
                        }
                    });
                }

                @Override
                public void onAdFailedToLoad(LoadAdError adError) {
                    appOpenAd = null; // Reset jika gagal dimuat
                }
            });
    }

    public boolean getPremiumStatus() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("premium_status", MODE_PRIVATE);
        return sharedPreferences.getBoolean("is_premium", false);
//        Log.d("Premium", "status: " + premium); // Tambahkan log ini
    }

    public void showPremiumDialog() {
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
                startActivity(new Intent(AdsActivity.this, PremiumActivity.class));
            }
        });

        // Menampilkan dialog
        premiumDialog.show();
    }
}
