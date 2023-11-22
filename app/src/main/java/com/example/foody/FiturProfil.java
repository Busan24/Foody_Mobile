package com.example.foody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FiturProfil extends AppCompatActivity {

    Dialog myDialog;
    private ViewSwitcher summarySwitcher;
    private Button btnAccount;
    private Button btnSummary;
    private int btnAccountTextColor;
    private int btnSummaryTextColor;
    BottomNavigationView bottomNavigationView;
    private ImageView logoutButton;

    private TextView logoutProfil;

    private TextView namaUserTextView, genderUserTextView, umurUserTextView;
    private TextView meanBmiTextView, beratTextView, tinggiTextView, lemakTextView;
    private TextView karbohidratTextView, proteinTextView, garamTextView, gulaTextView;
    private TextView batasKarboTextView, batasProteinTextView, batasLemakTextView, kebutuhanKaloriTextView;
    private TextView aktivitasTextView;
    private TextView namaAkun, emailAkun, usernameAkun, dateAkun, genderAkun;
    private TextView editName, editEmail, editUsername, editDate;
    private EditText epNama, epEmail, epUsername, epDate;
    private String authToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitur_profil);
        myDialog = new Dialog(this);

        Button btnEpProfile = findViewById(R.id.btn_edit_profile);
        summarySwitcher = findViewById(R.id.summary_switcher);
        btnAccount = findViewById(R.id.btn_Account);
        btnSummary = findViewById(R.id.btn_summary);
        logoutButton = findViewById(R.id.btn_logout);
        logoutProfil = findViewById(R.id.logout_profil);

        namaUserTextView = findViewById(R.id.nama_user);
        genderUserTextView = findViewById(R.id.gender_user);
        umurUserTextView = findViewById(R.id.umur_user);
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

        //Switch_Akun
        namaAkun = findViewById(R.id.akun_nama);
        emailAkun = findViewById(R.id.akun_email);
        usernameAkun = findViewById(R.id.akun_username);
        dateAkun = findViewById(R.id.akun_date);
        genderAkun = findViewById(R.id.akun_gender);

        // edit_profile
        editName = findViewById(R.id.ep_nama);
        editEmail = findViewById(R.id.ep_email);
        editUsername = findViewById(R.id.ep_username);
        editDate = findViewById(R.id.ep_date);

        authToken = getAuthToken();

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        // Melakukan permintaan profil pengguna dengan menyertakan token
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
                    genderUserTextView.setText(userData.getJenis_kelamin());
                    umurUserTextView.setText(String.valueOf(userData.getUsia()));
                    // Sesuaikan dengan atribut UserData dan SummaryData yang lain
                    meanBmiTextView.setText(decimalFormat.format(summaryData.getRata_rata_bmi()));;
                    beratTextView.setText(String.valueOf(userData.getBerat_badan()));
                    tinggiTextView.setText(String.valueOf(userData.getTinggi_badan()));

                    lemakTextView.setText(decimalFormat.format(summaryData.getTotal_lemak()));
                    karbohidratTextView.setText(decimalFormat.format(summaryData.getTotal_karbohidrat()));
                    proteinTextView.setText(decimalFormat.format(summaryData.getTotal_protein()));
                    garamTextView.setText(decimalFormat.format(summaryData.getTotal_garam()));
                    gulaTextView.setText(decimalFormat.format(summaryData.getTotal_gula()));

                    batasKarboTextView.setText(String.valueOf(summaryData.getBatas_karbohidrat()));
                    batasProteinTextView.setText(String.valueOf(summaryData.getBatas_protein()));
                    batasLemakTextView.setText(String.valueOf(summaryData.getBatas_lemak()));
                    kebutuhanKaloriTextView.setText(String.valueOf(summaryData.getKebutuhan_kalori()));
                    aktivitasTextView.setText(String.valueOf(summaryData.getAktivitas()));

                    // Switch_Akun
                    namaAkun.setText(userData.getName());
                    emailAkun.setText(userData.getEmail());
                    usernameAkun.setText(userData.getUsername());
                    dateAkun.setText(userData.getTanggla_lahir());
                    genderAkun.setText(userData.getJenis_kelamin());

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
                logoutUser();
            }
        });

        logoutProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Memanggil metode untuk melakukan logout
                logoutUser();
            }
        });

        // Inisialisasi warna teks
        btnAccountTextColor = ContextCompat.getColor(this, R.color.birucerah); // Warna teks untuk btn_Account
        btnSummaryTextColor = ContextCompat.getColor(this, R.color.birutua); // Warna teks untuk btn_Summary

        btnEpProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil metode showCustomDialog() ketika tombol btn_edit_profile ditekan
                showCustomDialog();
            }
        });

        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cek apakah sedang berada di tampilan activity_switch_akun
                if (summarySwitcher.getCurrentView() == summarySwitcher.findViewById(R.id.switch_akun)) {
                    // Tidak lakukan apa-apa karena sedang di tampilan activity_switch_akun
                } else {
                    // Beralih ke tampilan dari activity_switch_akun.xml
                    summarySwitcher.showNext();
                    btnAccount.setBackgroundResource(R.drawable.pp_oval_birutua);
                    btnAccount.setTextColor(btnAccountTextColor);
                    btnSummary.setBackgroundResource(R.drawable.pp_oval_birucerah);
                    btnSummary.setTextColor(btnSummaryTextColor);
                }
            }
        });

        btnSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cek apakah sedang berada di tampilan halaman_summary
                if (summarySwitcher.getCurrentView() == summarySwitcher.findViewById(R.id.halaman_summary)) {
                    // Tidak lakukan apa-apa karena sedang di tampilan halaman_summary
                } else {
                    // Beralih kembali ke tampilan pertama (Summary)
                    summarySwitcher.showPrevious();
                    btnAccount.setBackgroundResource(R.drawable.pp_oval_birucerah);
                    btnAccount.setTextColor(btnSummaryTextColor);
                    btnSummary.setBackgroundResource(R.drawable.pp_oval_birutua);
                    btnSummary.setTextColor(btnAccountTextColor);
                }
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
                } else if (item.getItemId() == R.id.nav_catatanku) {
                    // Buka halaman activity_fitur_catatanku
                    Intent intent = new Intent(FiturProfil.this, fitur_catatanku.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.nav_kalkulator) {
                    // Buka halaman KalkulatorBmi
                    Intent intent = new Intent(FiturProfil.this, KalkulatorBmi.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.nav_makanan) {
                    // Buka halaman FiturMakanan
                    Intent intent = new Intent(FiturProfil.this, FiturMakanan.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.nav_profil) {
                    // Buka halaman FiturProfil
                    return true;
                }
                return true;
            }

        });
    }

    private void showCustomDialog() {
        myDialog.setContentView(R.layout.popup_edit_profile);

        Window window = myDialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }

        epNama = myDialog.findViewById(R.id.ep_nama);
        epEmail = myDialog.findViewById(R.id.ep_email);
        epUsername = myDialog.findViewById(R.id.ep_username);
        epDate = myDialog.findViewById(R.id.ep_date);

        getProfileDetails(authToken);  // Mendapatkan data profil untuk diisi dalam form edit profil

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Tampilkan dialog
        myDialog.show();

        Button btnSave = myDialog.findViewById(R.id.btn_ep_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dapatkan nilai-nilai baru dari input pengguna
                String newName = epNama.getText().toString();
                String newEmail = epEmail.getText().toString();
                String newUsername = epUsername.getText().toString();
                String newBirthdate = epDate.getText().toString();

                // Panggil metode pembaruan profil
                updateProfile(authToken, newName, newEmail, newUsername, newBirthdate);

                // Sisanya dari dialog (menutup dialog, dll.)
                myDialog.dismiss();
            }
        });

        Button batalButton = myDialog.findViewById(R.id.btn_close);
        batalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
    }

    private void getProfileDetails(String authToken) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        // Mendapatkan data profil pengguna dengan menyertakan token
        Call<UserProfile> call = apiService.getUserProfile("Bearer " + authToken);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful()) {
                    UserProfile userProfile = response.body();
                    UserData userData = userProfile.getData();

                    // Mengisi data dalam form edit profil
                    epNama.setText(userData.getName());
                    epEmail.setText(userData.getEmail());
                    epUsername.setText(userData.getUsername());
                    epDate.setText(userData.getTanggla_lahir());
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
    }

    private void updateProfile(String authToken, String newName, String newEmail, String newUsername, String newBirthdate) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        // Panggilan ini untuk mendapatkan data profil pengguna saat ini
        Call<UserProfile> currentProfileCall = apiService.getUserProfile("Bearer " + authToken);
        currentProfileCall.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful()) {
                    UserProfile currentProfile = response.body();
                    if (currentProfile != null) {
                        UserData userData = currentProfile.getData();

                        // Memeriksa apakah ada perubahan pada nama, email, dan username
                        boolean isNameChanged = !TextUtils.isEmpty(newName) && !newName.equals(userData.getName());
                        boolean isEmailChanged = !TextUtils.isEmpty(newEmail) && !newEmail.equals(userData.getEmail());
                        boolean isUsernameChanged = !TextUtils.isEmpty(newUsername) && !newUsername.equals(userData.getUsername());
                        boolean isBirthdateChanged = !TextUtils.isEmpty(newBirthdate) && !newBirthdate.equals(userData.getTanggla_lahir());

                        if (!isNameChanged && !isEmailChanged && !isUsernameChanged && !isBirthdateChanged) {
                            // Tidak ada perubahan pada profil
                            Toast.makeText(FiturProfil.this, "Tidak ada perubahan pada profil.", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        // Jika ada perubahan, lanjutkan dengan pembaruan profil
                        performPartialUpdate(apiService, authToken, isNameChanged, isEmailChanged, isUsernameChanged, newName, newEmail, newUsername, newBirthdate);
                    } else {
                        Toast.makeText(FiturProfil.this, "Gagal memperbarui profil. Coba lagi.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FiturProfil.this, "Gagal memperbarui profil. Coba lagi.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                // Tanggapi kesalahan pada permintaan
                Toast.makeText(FiturProfil.this, "Gagal memperbarui profil. Periksa koneksi internet Anda.", Toast.LENGTH_SHORT).show();
                Log.e("Update Profile Error", "Gagal memperbarui profil", t);
            }
        });
    }

    private void performPartialUpdate(ApiService apiService, String authToken, boolean isNameChanged, boolean isEmailChanged, boolean isUsernameChanged, String newName, String newEmail, String newUsername, String newBirthdate) {
        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
        if (isNameChanged) {
            updateProfileRequest.setName(newName);
        }
        if (isEmailChanged) {
            updateProfileRequest.setEmail(newEmail);
        }
        if (isUsernameChanged) {
            updateProfileRequest.setUsername(newUsername);
        }
        updateProfileRequest.setTanggal_lahir(newBirthdate);

        Call<UpdateProfileResponse> call = apiService.updateProfile("Bearer " + authToken, updateProfileRequest);
        call.enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                if (response.isSuccessful()) {
                    UpdateProfileResponse updateProfileResponse = response.body();
                    // Tanggapi respons berhasil
                    String message = updateProfileResponse.getMessage();
                    if (message != null) {
                        Toast.makeText(FiturProfil.this, message, Toast.LENGTH_SHORT).show();
                    } else {
                        // Tanggapi jika pesan adalah null (sesuaikan dengan kebutuhan Anda)
                        Toast.makeText(FiturProfil.this, "Profil berhasil diperbarui.", Toast.LENGTH_SHORT).show();
                    }

                    // Refresh tampilan profil setelah berhasil memperbarui
                    refreshUserProfile(authToken);
//                    Intent intent = new Intent(FiturProfil.this, FiturProfil.class);
//                    startActivity(intent);
//                    finish();
                } else {
                    // Tanggapi respons gagal
                    Toast.makeText(FiturProfil.this, "Gagal memperbarui profil. Coba lagi.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                // Tanggapi kesalahan pada permintaan
                Toast.makeText(FiturProfil.this, "Gagal memperbarui profil. Periksa koneksi internet Anda.", Toast.LENGTH_SHORT).show();
                Log.e("Update Profile Error", "Gagal memperbarui profil", t);
            }
        });
    }




    private void refreshUserProfile(String authToken) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        // Melakukan permintaan profil pengguna dengan menyertakan token
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
                    genderUserTextView.setText(userData.getJenis_kelamin());
                    umurUserTextView.setText(String.valueOf(userData.getUsia()));
                    // Sesuaikan dengan atribut UserData dan SummaryData yang lain
                    meanBmiTextView.setText(decimalFormat.format(summaryData.getRata_rata_bmi()));;
                    beratTextView.setText(String.valueOf(userData.getBerat_badan()));
                    tinggiTextView.setText(String.valueOf(userData.getTinggi_badan()));

                    lemakTextView.setText(decimalFormat.format(summaryData.getTotal_lemak()));
                    karbohidratTextView.setText(decimalFormat.format(summaryData.getTotal_karbohidrat()));
                    proteinTextView.setText(decimalFormat.format(summaryData.getTotal_protein()));
                    garamTextView.setText(decimalFormat.format(summaryData.getTotal_garam()));
                    gulaTextView.setText(decimalFormat.format(summaryData.getTotal_gula()));

                    batasKarboTextView.setText(String.valueOf(summaryData.getBatas_karbohidrat()));
                    batasProteinTextView.setText(String.valueOf(summaryData.getBatas_protein()));
                    batasLemakTextView.setText(String.valueOf(summaryData.getBatas_lemak()));
                    kebutuhanKaloriTextView.setText(String.valueOf(summaryData.getKebutuhan_kalori()));
                    aktivitasTextView.setText(String.valueOf(summaryData.getAktivitas()));

                    // Switch_Akun
                    namaAkun.setText(userData.getName());
                    emailAkun.setText(userData.getEmail());
                    usernameAkun.setText(userData.getUsername());
                    dateAkun.setText(userData.getTanggla_lahir());
                    genderAkun.setText(userData.getJenis_kelamin());
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
    }



    public void showDatePickerDialog(View view) {
        final EditText dateEditText = (EditText) view; // Mengonversi View menjadi EditText

        // Mendapatkan tanggal sekarang
        int year, month, day;
        String dateText = dateEditText.getText().toString();
        if (dateText.isEmpty()) {
            // Jika EditText kosong, gunakan tanggal sekarang
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        } else {
            // Jika EditText sudah memiliki tanggal, gunakan tanggal yang ada
            String[] dateParts = dateText.split("-");
            year = Integer.parseInt(dateParts[0]);
            month = Integer.parseInt(dateParts[1]) - 1;
            day = Integer.parseInt(dateParts[2]);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                // Menangani pemilihan tanggal
                String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                dateEditText.setText(selectedDate);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    // Metode untuk melakukan logout
    private void logoutUser() {
        // Mendapatkan objek ApiService
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        // Melakukan permintaan logout
        Call<Void> call = apiService.logoutUser();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Mendapatkan objek SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("login_status", MODE_PRIVATE);

                    // Menghapus preferensi login
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("is_logged_in");
                    editor.apply();
                    // Jika logout berhasil, pindah ke halaman hal_awal
                    Toast.makeText(FiturProfil.this, "Logout berhasil!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FiturProfil.this, hal_awal.class);
                    startActivity(intent);
                    finish(); // Menutup activity saat ini agar tidak dapat kembali ke FiturProfil melalui tombol back
                } else {
                    // Tangani kesalahan pada respons
                    Toast.makeText(FiturProfil.this, "Logout gagal. Coba lagi.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Tangani kesalahan pada permintaan
                Toast.makeText(FiturProfil.this, "Logout gagal. Periksa koneksi internet Anda.", Toast.LENGTH_SHORT).show();

                // Tambahkan log untuk melihat kesalahan pada logcat
                Log.e("Logout Error", "Logout gagal", t);
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