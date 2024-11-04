package com.orion.foody;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.DatePicker;
import android.widget.Button;
import android.widget.EditText;

import android.app.DatePickerDialog;

import android.content.Intent;
import android.content.SharedPreferences;

import android.util.Log;

import android.os.Bundle;
import android.os.Environment;

import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import okhttp3.ResponseBody;

import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReportPdfActivity extends AppCompatActivity {

    EditText dateFrom, dateTo;
    Button buttonBack, buttonDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report_pdf);

        dateFrom = findViewById(R.id.date_from);
        dateTo = findViewById(R.id.date_to);
        buttonBack = findViewById(R.id.btn_close);
        buttonDownload = findViewById(R.id.btn_download);

        buttonBack.setOnClickListener(view -> {
            finish();
        });

        dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(dateFrom);
            }
        });

        dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(dateTo);
            }
        });


        buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadPdf();
            }
        });


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

    private void downloadPdf() {
        ReportPdfRequestModel reportPdfRequestModel = new ReportPdfRequestModel(dateFrom.getText().toString(), dateTo.getText().toString());
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<ResponseBody> call = apiService.reportPdf("Bearer " + getAuthToken(), reportPdfRequestModel);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String fileName = reportPdfRequestModel.getTo().equals("")  && reportPdfRequestModel.getFrom().equals("") ? "Report Catatan Makanan.pdf" : "Report Catatan Makanan " + reportPdfRequestModel.getFrom() + " - " + reportPdfRequestModel.getTo() + ".pdf";
                    savePdf(response.body(), fileName);
                    finish();
                }
            }

            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void savePdf(ResponseBody body, String fileName) {
        // Mendapatkan direktori penyimpanan
        File pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);

        try (InputStream inputStream = body.byteStream();
             FileOutputStream fileOutputStream = new FileOutputStream(pdfFile)) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            // Membaca dari InputStream dan menulis ke FileOutputStream
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            Toast.makeText(this, "File berhasil disimpan di: " + pdfFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("Download", "Gagal menyimpan file: " + e.getMessage());
        }
    }

    private String getAuthToken() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("auth_token", MODE_PRIVATE);
        String authToken = sharedPreferences.getString("token", "");
        Log.d("AuthToken", "Token: " + authToken); // Tambahkan log ini
        return authToken;
    }
}