package com.kasra.picker.utils;

import android.content.Context;
import android.graphics.Typeface;

import androidx.collection.SimpleArrayMap;

import java.util.Locale;


public class FontUtils {
    private static String FONT_NAME = "font_regular";

    private static final SimpleArrayMap<String, Typeface> cache = new SimpleArrayMap<>();

    public static Typeface Default(Context context) {
        synchronized (cache) {
            if (!cache.containsKey(FONT_NAME)) {
                Typeface t;
                if (Locale.getDefault().toString().toLowerCase().equals("fa_IR")
                        || Locale.getDefault().toString().toLowerCase().equals("fa")
                ) {
                    t = Typeface.createFromAsset(context.getAssets(), "fonts/" + "fa_font_regular" + ".ttf");
                } else {
                    t = Typeface.createFromAsset(context.getAssets(), "fonts/" + "en_font_regular" + ".ttf");
                }
                cache.put(FONT_NAME, t);
                return t;
            }
            return cache.get(FONT_NAME);
        }
    }
}
