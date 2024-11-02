package com.example.foody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.view.ViewGroup;

import android.widget.DatePicker;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.text.TextUtils;

import android.app.DatePickerDialog;

import android.content.Intent;
import android.content.SharedPreferences;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Calendar;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.bumptech.glide.Glide;

public class PopupEditProfileActivity extends AdsActivity {
    Button buttonBack;
    private ArrayAdapter<String> aktivitasAdapter;
    private EditText epNama, epEmail, epUsername, epDate;
    private Spinner aktivitasSpinner;

    private List<String> aktivitasList;
    private List<String> jenisKelaminList;
    private Spinner jenisKelaminSpinner;
    private ArrayAdapter<String> jenisKelaminAdapter;
    private String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.popup_edit_profile, findViewById(R.id.content_frame));
//        setContentView(R.layout.popup_edit_profile);

        buttonBack = findViewById(R.id.btn_close);

        buttonBack.setOnClickListener(view -> {
            finish();
        });

        epDate = findViewById(R.id.ep_date);


        epDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(epDate);
            }
        });

        authToken = getAuthToken();

        aktivitasSpinner = findViewById(R.id.spinner_aktivitas);
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

        jenisKelaminSpinner = findViewById(R.id.spinner_jenis_kelamin);
        ArrayList<String> jenisKelaminList = new ArrayList<>();
        jenisKelaminList.add("Jenis Kelamin");
        jenisKelaminList.add("Laki-laki");
        jenisKelaminList.add("Perempuan");

        // Inisialisasi aktivitasList terlebih dahulu
        aktivitasList = new ArrayList<>();
        aktivitasList.add("Aktivitas");
        aktivitasList.add("Tidak aktif (tidak berolahraga sama sekali)");
        aktivitasList.add("Cukup aktif (berolahraga 1-3x seminggu)");
        aktivitasList.add("Aktif (berolahraga 3-5x seminggu)");
        aktivitasList.add("Sangat aktif (berolahraga 6-7x seminggu)");
        aktivitasList.add("Super Aktif (berolahraga 1-2x setiap hari)");

// Setelah itu, buat dan atur adapter
        aktivitasSpinner = findViewById(R.id.spinner_aktivitas);
        aktivitasAdapter = new ArrayAdapter<>(this, R.layout.spinner_hint_item, aktivitasList);
        aktivitasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aktivitasSpinner.setAdapter(aktivitasAdapter);

        jenisKelaminAdapter = new ArrayAdapter<>(this, R.layout.spinner_hint_item, jenisKelaminList);
        jenisKelaminAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jenisKelaminSpinner.setAdapter(jenisKelaminAdapter);

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

        epNama = findViewById(R.id.ep_nama);

        getProfileDetails(authToken);

        Button btnSave = findViewById(R.id.btn_ep_save);
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
            }
        });


//        Button batalButton = findViewById(R.id.btn_close);
//        batalButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                refreshUserProfile(authToken);
//            }
//        });
    }

    private void showDatePickerDialog(final EditText epDate) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Format tanggal yang dipilih sesuai dengan format yang Anda inginkan
                        String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                        epDate.setText(selectedDate);
                    }
                },
                year, month, day
        );

        datePickerDialog.show();
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
                    SummaryData summaryData = userProfile.getSummary();

                    android.util.Log.e("API", "onResponse: " + userData.getJenis_kelamin());
                    // Mengisi data dalam form edit profil
                    epNama.setText(userData.getName());
//                    epEmail.setText(userData.getEmail());
//                    epUsername.setText(userData.getUsername());
                    epDate.setText(userData.getTanggla_lahir());

                    int positionGender = jenisKelaminAdapter.getPosition(userData.getJenis_kelamin());
                    jenisKelaminSpinner.setSelection(positionGender);

                    int position = aktivitasAdapter.getPosition(getStringForAktivitas(summaryData.getAktivitas()));
                    aktivitasSpinner.setSelection(position);


                } else {
                    // Tangani kesalahan pada respons
                    Toast.makeText(PopupEditProfileActivity.this, "Gagal mengambil profil. Coba lagi.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                // Tangani kesalahan pada permintaan

                Toast.makeText(PopupEditProfileActivity.this, "Gagal mengambil profil. Periksa koneksi internet Anda.", Toast.LENGTH_SHORT).show();
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
//                            Toast.makeText(PopupEditProfileActivity.this, "Tidak ada perubahan pada profil.", Toast.LENGTH_SHORT).show();
//                            return;
//                        }

                        // Jika ada perubahan, lanjutkan dengan pembaruan profil
                        performPartialUpdate(apiService, authToken, isNameChanged, isBirthdateChanged, isAktivitasChanged, isJenisKelaminChanged, newName, newBirthdate, newAktivitas, newJenisKelamin, userData.getName(), userData.getTanggla_lahir(), summaryData.getAktivitas(), userData.getJenis_kelamin());
                    } else {
                        Toast.makeText(PopupEditProfileActivity.this, "Gagal memperbarui profil. Coba lagi.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PopupEditProfileActivity.this, "Gagal memperbarui profil. Coba lagi.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                // Tanggapi kesalahan pada permintaan
                Toast.makeText(PopupEditProfileActivity.this, "Gagal memperbarui profil. Periksa koneksi internet Anda.", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(PopupEditProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                    } else {
                        // Tanggapi jika pesan adalah null (sesuaikan dengan kebutuhan Anda)
                        Toast.makeText(PopupEditProfileActivity.this, "Profil berhasil diperbarui.", Toast.LENGTH_SHORT).show();
                    }
                    new Handler().postDelayed(() -> finish(), 1000);

                    // Refresh tampilan profil setelah berhasil memperbarui
//                    refreshUserProfile(authToken);
                } else {
                    // Tanggapi respons gagal
                    Toast.makeText(PopupEditProfileActivity.this, "Gagal memperbarui profil. Coba lagi.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                // Tanggapi kesalahan pada permintaan
                Toast.makeText(PopupEditProfileActivity.this, "Gagal memperbarui profil. Periksa koneksi internet Anda.", Toast.LENGTH_SHORT).show();
                Log.e("Update Profile Error", "Gagal memperbarui profil", t);
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
