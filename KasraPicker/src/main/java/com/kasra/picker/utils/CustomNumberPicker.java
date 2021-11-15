package com.kasra.picker.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.kasra.picker.R;

import java.util.Locale;

public class CustomNumberPicker extends android.widget.NumberPicker {

    Typeface type;

    public CustomNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int index,
                        android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (Locale.getDefault().toString().toLowerCase().equals("fa_IR")
                || Locale.getDefault().toString().toLowerCase().equals("fa")
        ) {
            type = Typeface.createFromAsset(getContext().getAssets(),
                    "fonts/fa_font_regular.ttf");
        }else{
            type = Typeface.createFromAsset(getContext().getAssets(),
                    "fonts/en_font_regular.ttf");
        }
        updateView(child);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);

        if (Locale.getDefault().toString().toLowerCase().equals("fa_IR")
                || Locale.getDefault().toString().toLowerCase().equals("fa")
        ) {
            type = Typeface.createFromAsset(getContext().getAssets(),
                    "fonts/fa_font_regular.ttf");
        }else{
            type = Typeface.createFromAsset(getContext().getAssets(),
                    "fonts/en_font_regular.ttf");
        }
        updateView(child);
    }

    private void updateView(View view) {

        if (view instanceof EditText) {
            ((EditText) view).setTypeface(type);
            ((EditText) view).setTextSize(17);

        }

    }

}
