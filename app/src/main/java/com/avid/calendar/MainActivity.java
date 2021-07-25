package com.avid.calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;

import com.kasra.picker.customviews.DateRangeCalendarView;
import com.kasra.picker.dialog.DatePickerDialog;
import com.kasra.picker.dialog.TimePickerDialog;
import com.kasra.picker.utils.PersianCalendar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatButton button = findViewById(R.id.my_btn);
        AppCompatButton timeBtn = findViewById(R.id.time_btn);
        button.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                datePickerDialog = new DatePickerDialog(MainActivity.this);
                datePickerDialog.setSelectionMode(DateRangeCalendarView.SelectionMode.Range, DateRangeCalendarView.HolidayMode.Enable);

                datePickerDialog.setCanceledOnTouchOutside(true);
                datePickerDialog.setOnRangeDateSelectedListener(new DatePickerDialog.OnRangeDateSelectedListener() {
                    @Override
                    public void onRangeDateSelected(PersianCalendar startDate, PersianCalendar endDate) {
//                        txtStartDate.setText(startDate.getPersianShortDate());
//                        txtEndDate.setText(endDate.getPersianShortDate());
                    }
                });

                datePickerDialog.showDialog();
            }


        });
        timeBtn.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.TimePickerCallback() {
                @Override
                public void onTimeSelected(int hours, int mins) {

                }

                @Override
                public void onCancel() {

                }
            });

            timePickerDialog.showDialog();
        });

    }
}