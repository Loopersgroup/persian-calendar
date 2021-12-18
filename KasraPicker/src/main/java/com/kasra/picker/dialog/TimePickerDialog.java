package com.kasra.picker.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.kasra.picker.R;


import java.util.Calendar;

public class TimePickerDialog extends Dialog {
    AppCompatImageView tvDialogCancel;
    AppCompatButton tvDialogDone;

    NumberPicker hourPicker;
    NumberPicker minutePicker;
    private String mTitle;
    private int hours, minutes;
    AppCompatTextView hourEditTxt;
    AppCompatTextView minuteEditTxt;
    Context mContext;


    public interface TimePickerCallback {
        void onTimeSelected(int hours, int mins);

        void onCancel();
    }

    private TimePickerCallback onTimeChangedListener;

    public TimePickerDialog(Context context, TimePickerCallback timePickerCallback) {
        super(context);
        mContext = context;
        this.onTimeChangedListener = timePickerCallback;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setGravity(Gravity.CENTER);
        setCanceledOnTouchOutside(false);

        initView();

        setListeners();

        //Grab the window of the dialog, and change the width
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = this.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    private void initView() {
        hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        minutes = Calendar.getInstance().get(Calendar.MINUTE);
        View view= LayoutInflater.from(mContext).inflate(R.layout.dialog_time_picker,null);
        setContentView(view);
        tvDialogDone = view.findViewById(R.id.submit_btn);
        tvDialogCancel = view.findViewById(R.id.cancel_btn);
        hourPicker = view.findViewById(R.id.hour_picker);
        minutePicker =view.findViewById(R.id.minute_picker);
        hourEditTxt =view. findViewById(R.id.hour_time_txt);
        minuteEditTxt =view. findViewById(R.id.minutes_time_txt);
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        hourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                hours = newVal;
                hourEditTxt.setText(setNumberEditText(String.valueOf(newVal)));
            }
        });
        minutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                minutes=newVal;
                minuteEditTxt.setText(setNumberEditText(String.valueOf(newVal)));
            }
        });

    }
    public void initHour(int hours){
        this.hours=hours;
    }
    public void initMinute(int minutes){
        this.minutes=minutes;
    }

    private void setListeners() {
        tvDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTimeChangedListener != null) {
                    onTimeChangedListener.onCancel();
                }
                TimePickerDialog.this.dismiss();
            }
        });

        tvDialogDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTimeChangedListener != null) {
                    onTimeChangedListener.onTimeSelected(hours, minutes);
                }
                TimePickerDialog.this.dismiss();
            }
        });
    }

    public void showDialog() {

        hourEditTxt.setText(setNumberEditText(String.valueOf(hours)));
        minuteEditTxt.setText(setNumberEditText(String.valueOf(minutes)));
        hourPicker.setValue(hours);
        minutePicker.setValue(minutes);
        this.show();
    }

    String setNumberEditText(String number) {
        if (Integer.parseInt(number) < 10) {
            return "0" + number;
        } else {
            return number;
        }
    }
}
