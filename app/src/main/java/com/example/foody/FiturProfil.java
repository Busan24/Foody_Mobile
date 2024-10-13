package com.example.foody;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import androidx.core.content.ContextCompat;

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
import okhttp3.ResponseBody;
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

public class FiturProfil extends AppCompatActivity {

    Dialog myDialog;
    private ViewSwitcher summarySwitcher;
    private ArrayAdapter<String> aktivitasAdapter;
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
    private TextView namaAkun, emailAkun, dateAkun, genderAkun;
    private TextView editName, editEmail, editUsername, editDate;
    private EditText epNama, epEmail, epUsername, epDate;
    private Spinner aktivitasSpinner;
    private static final int PICK_IMAGE_REQUEST = 1;

    private ShapeableImageView fotoProfil;

    private ImageView foto_profil;

    private List<String> aktivitasList;
    private List<String> jenisKelaminList;
    private Spinner jenisKelaminSpinner;
    private ArrayAdapter<String> jenisKelaminAdapter;
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
        fotoProfil = findViewById(R.id.foto_profil);

        //Switch_Akun
        namaAkun = findViewById(R.id.akun_nama);
        emailAkun = findViewById(R.id.akun_email);
//        usernameAkun = findViewById(R.id.akun_username);
        dateAkun = findViewById(R.id.akun_date);
        genderAkun = findViewById(R.id.akun_gender);

        foto_profil = findViewById(R.id.foto_profil);

        // edit_profile
        editName = findViewById(R.id.ep_nama);
//        editEmail = findViewById(R.id.ep_email);
//        editUsername = findViewById(R.id.ep_username);
        editDate = findViewById(R.id.ep_date);

        authToken = getAuthToken();

//        refreshUserProfile(authToken);

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

                    batasKarboTextView.setText(decimalFormat.format(summaryData.getBatas_karbohidrat()));
                    batasProteinTextView.setText(decimalFormat.format(summaryData.getBatas_protein()));
                    batasLemakTextView.setText(decimalFormat.format(summaryData.getBatas_lemak()));
                    kebutuhanKaloriTextView.setText(decimalFormat.format(summaryData.getKebutuhan_kalori()));
                    aktivitasTextView.setText(String.valueOf(summaryData.getAktivitas()));

                    // Switch_Akun
                    namaAkun.setText(userData.getName());
                    emailAkun.setText(userData.getEmail());
//                    usernameAkun.setText(userData.getUsername());
                    dateAkun.setText(userData.getTanggla_lahir());
                    genderAkun.setText(userData.getJenis_kelamin());


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

        myDialog.setContentView(R.layout.popup_edit_profile);

        // Inisialisasi aktivitasList terlebih dahulu
        aktivitasList = new ArrayList<>();
        aktivitasList.add("Aktivitas");
        aktivitasList.add("Tidak aktif (tidak berolahraga sama sekali)");
        aktivitasList.add("Cukup aktif (berolahraga 1-3x seminggu)");
        aktivitasList.add("Aktif (berolahraga 3-5x seminggu)");
        aktivitasList.add("Sangat aktif (berolahraga 6-7x seminggu)");
        aktivitasList.add("Super Aktif (berolahraga 1-2x setiap hari)");

// Setelah itu, buat dan atur adapter
        aktivitasSpinner = myDialog.findViewById(R.id.spinner_aktivitas);
        aktivitasAdapter = new ArrayAdapter<>(this, R.layout.spinner_hint_item, aktivitasList);
        aktivitasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aktivitasSpinner.setAdapter(aktivitasAdapter);

        // Initialize jenisKelaminAdapter with your data (similar to what you did in showCustomDialog)
        jenisKelaminList = new ArrayList<>();
        jenisKelaminList.add("Jenis Kelamin");
        jenisKelaminList.add("Laki-laki");
        jenisKelaminList.add("Perempuan");

        jenisKelaminSpinner = myDialog.findViewById(R.id.spinner_jenis_kelamin);
        jenisKelaminAdapter = new ArrayAdapter<>(this, R.layout.spinner_hint_item, jenisKelaminList);
        jenisKelaminAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jenisKelaminSpinner.setAdapter(jenisKelaminAdapter);

// Terakhir, tampilkan dialog

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    Toast.makeText(FiturProfil.this, "Gagal mengupload gambar", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                // Handle kesalahan jaringan atau server
                Toast.makeText(FiturProfil.this, "Terjadi kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showCustomDialog() {
        myDialog.setContentView(R.layout.popup_edit_profile);

        aktivitasSpinner = myDialog.findViewById(R.id.spinner_aktivitas);
        ArrayList<String> aktivitasList = new ArrayList<>();
        aktivitasList.add("Aktivitas");
        aktivitasList.add("Tidak aktif (tidak berolahraga sama sekali)");
        aktivitasList.add("Cukup aktif (berolahraga 1-3x seminggu)");
        aktivitasList.add("Aktif (berolahraga 3-5x seminggu)");
        aktivitasList.add("Sangat aktif (berolahraga atau 6-7x seminggu)");
        aktivitasList.add("Super Aktif (berolahraga 1-2x setiap hari)");

        ArrayList<Double> aktivitasValues = new ArrayList<>();
        aktivitasValues.add(0.0); // Placeholder untuk aktivitas
        aktivitasValues.add(1.200); // Tidak aktif
        aktivitasValues.add(1.375); // Cukup aktif
        aktivitasValues.add(1.550); // Aktif
        aktivitasValues.add(1.725); // Sangat aktif
        aktivitasValues.add(1.9); // super

        jenisKelaminSpinner = myDialog.findViewById(R.id.spinner_jenis_kelamin);
        ArrayList<String> jenisKelaminList = new ArrayList<>();
        jenisKelaminList.add("Jenis Kelamin");
        jenisKelaminList.add("Laki-laki");
        jenisKelaminList.add("Perempuan");

        ArrayAdapter<String> jenisKelaminAdapter = new ArrayAdapter<String>(this, R.layout.spinner_hint_item, jenisKelaminList) {
            @Override
            public boolean isEnabled(int position) {
                // Make the "Jenis Kelamin" item unselectable
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;

                if (position == 0) {
                    // If it's the "Jenis Kelamin" item, set text color to gray
                    textView.setTextColor(ContextCompat.getColor(getContext(), android.R.color.darker_gray));
                } else {
                    textView.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
                }

                return view;
            }
        };

        jenisKelaminAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jenisKelaminSpinner.setAdapter(jenisKelaminAdapter);

// Set the initial selection to the "Jenis Kelamin" item
//        jenisKelaminSpinner.setSelection(0);

        ArrayAdapter<String> aktivitasAdapter = new ArrayAdapter<String>(this, R.layout.spinner_hint_item, aktivitasList) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;

                if (position == 0) {
                    textView.setTextColor(ContextCompat.getColor(getContext(), android.R.color.darker_gray));
                } else {
                    textView.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
                }

                return view;
            }
        };

        aktivitasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aktivitasSpinner.setAdapter(aktivitasAdapter);

        aktivitasSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {
                    Double selectedAktivitasValue = aktivitasValues.get(position);
//                    Toast.makeText(registrasi.this, "Aktivitas: " + selectedAktivitasValue, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Window window = myDialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }



        epNama = myDialog.findViewById(R.id.ep_nama);
//        epEmail = myDialog.findViewById(R.id.ep_email);
//        epUsername = myDialog.findViewById(R.id.ep_username);
        epDate = myDialog.findViewById(R.id.ep_date);

        getProfileDetails(authToken);

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Tampilkan dialog
        myDialog.show();


        Button btnSave = myDialog.findViewById(R.id.btn_ep_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dapatkan nilai-nilai baru dari input pengguna
                String newName = epNama.getText().toString();
//                String newEmail = epEmail.getText().toString();
//                String newUsername = epUsername.getText().toString();
                String newBirthdate = epDate.getText().toString();

                int selectedJenisKelaminPosition = jenisKelaminSpinner.getSelectedItemPosition();
                String newJenisKelamin = jenisKelaminList.get(selectedJenisKelaminPosition);

                int selectedAktivitasPosition = aktivitasSpinner.getSelectedItemPosition();
                Double newAktivitas = aktivitasValues.get(selectedAktivitasPosition);

                updateProfile(authToken, newName, newBirthdate, newAktivitas, newJenisKelamin);
                myDialog.dismiss();
            }
        });


        TextView batalButton = myDialog.findViewById(R.id.btn_close);
        batalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                refreshUserProfile(authToken);
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

    // Perubahan pada fungsi getProfileDetails
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
                    SummaryData summaryData = userProfile.getSummary();

                    // Mengisi data dalam form edit profil
                    epNama.setText(userData.getName());
//                    epEmail.setText(userData.getEmail());
//                    epUsername.setText(userData.getUsername());
                    epDate.setText(userData.getTanggla_lahir());

                    int positionGender = jenisKelaminAdapter.getPosition(userData.getJenis_kelamin());
                    jenisKelaminSpinner.setSelection(positionGender);

                    int position = aktivitasAdapter.getPosition(getStringForAktivitas(summaryData.getAktivitas()));
                    aktivitasSpinner.setSelection(position);

                    // Menyimpan dan menampilkan gambar pada foto_profil
                    if (userData.getGambar() != null && !userData.getGambar().isEmpty()) {
                        Glide.with(FiturProfil.this).load(userData.getGambar()).into(foto_profil);
                    } else {
                        // Handle jika tidak ada gambar profil
                        // Contoh: Load default image
                        Glide.with(FiturProfil.this).load(R.drawable.profil_user).into(foto_profil);
                    }

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

    private void updateProfile(String authToken, String newName, String newBirthdate, Double newAktivitas, String newJenisKelamin) {
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
                        SummaryData summaryData = currentProfile.getSummary();

                        // Memeriksa apakah ada perubahan pada nama, email, username, dan tanggal lahir
                        boolean isNameChanged = !TextUtils.isEmpty(newName) && !newName.equals(userData.getName());
//                        boolean isEmailChanged = !TextUtils.isEmpty(newEmail) && !newEmail.equals(userData.getEmail());
//                        boolean isUsernameChanged = !TextUtils.isEmpty(newUsername) && !newUsername.equals(userData.getUsername());
                        boolean isBirthdateChanged = !TextUtils.isEmpty(newBirthdate) && !newBirthdate.equals(userData.getTanggla_lahir());

                        // Memeriksa apakah ada perubahan pada aktivitas
                        boolean isAktivitasChanged = !newAktivitas.equals(summaryData.getAktivitas());

                        // Check for changes in jenis_kelamin
                        boolean isJenisKelaminChanged = !newJenisKelamin.equals(userData.getJenis_kelamin());

//                        if (!isNameChanged && !isEmailChanged && !isBirthdateChanged && !isAktivitasChanged) {
//                            // Tidak ada perubahan pada profil
//                            Toast.makeText(FiturProfil.this, "Tidak ada perubahan pada profil.", Toast.LENGTH_SHORT).show();
//                            return;
//                        }

                        // Jika ada perubahan, lanjutkan dengan pembaruan profil
                        performPartialUpdate(apiService, authToken, isNameChanged, isBirthdateChanged, isAktivitasChanged, isJenisKelaminChanged, newName, newBirthdate, newAktivitas, newJenisKelamin, userData.getName(), userData.getTanggla_lahir(), summaryData.getAktivitas(), userData.getJenis_kelamin());
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

    private void performPartialUpdate(ApiService apiService, String authToken, boolean isNameChanged, boolean isBirthdateChanged, boolean isAktivitasChanged, boolean isJenisKelaminChanged, String newName, String newBirthdate, Double newAktivitas, String newJenisKelamin, String oldName, String oldBirthDate, double oldAktivitas, String oldJenisKelamin) {
        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
        if (isNameChanged) {
            updateProfileRequest.setName(newName);
        }
//        if (isEmailChanged) {
//            updateProfileRequest.setEmail(newEmail);
//        }
//        if (isUsernameChanged) {
//            updateProfileRequest.setUsername(newUsername);
//        }
        if (isBirthdateChanged) {
            updateProfileRequest.setTanggal_lahir(newBirthdate);
        }
        if (isJenisKelaminChanged) {
            updateProfileRequest.setJenisKelamin(newJenisKelamin);
        }
        if (isAktivitasChanged) {
            updateProfileRequest.setAktivitas(newAktivitas);
        }
        if (!isNameChanged) {
            updateProfileRequest.setName(oldName);
        }
        if (!isBirthdateChanged) {
            updateProfileRequest.setTanggal_lahir(oldBirthDate);
        }
        if (!isJenisKelaminChanged || newJenisKelamin.equals("Jenis Kelamin")) {
            updateProfileRequest.setJenisKelamin(oldJenisKelamin);
        }
        if (!isAktivitasChanged) {
            updateProfileRequest.setAktivitas(oldAktivitas);
        }


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

                    batasKarboTextView.setText(decimalFormat.format(summaryData.getBatas_karbohidrat()));
                    batasProteinTextView.setText(decimalFormat.format(summaryData.getBatas_protein()));
                    batasLemakTextView.setText(decimalFormat.format(summaryData.getBatas_lemak()));
                    kebutuhanKaloriTextView.setText(decimalFormat.format(summaryData.getKebutuhan_kalori()));
                    aktivitasTextView.setText(String.valueOf(summaryData.getAktivitas()));

                    // Switch_Akun
                    namaAkun.setText(userData.getName());
//                    emailAkun.setText(userData.getEmail());
//                    usernameAkun.setText(userData.getUsername());
                    dateAkun.setText(userData.getTanggla_lahir());
                    genderAkun.setText(userData.getJenis_kelamin());
                    tampilkanProfil(userData);
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