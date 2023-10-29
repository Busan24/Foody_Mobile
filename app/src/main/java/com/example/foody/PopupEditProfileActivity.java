package com.example.foody;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.app.DatePickerDialog;
import java.util.Calendar;

public class PopupEditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_edit_profile);

        EditText epDate = findViewById(R.id.ep_date);

        epDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(epDate);
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
}
