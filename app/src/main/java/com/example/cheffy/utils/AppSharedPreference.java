package com.example.cheffy.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPreference {
    static SharedPreferences instance = null;

    private AppSharedPreference() {
    }

    public synchronized static SharedPreferences getInstance(Context context) {
        if (instance == null) {
            instance = context.getSharedPreferences("AppSharedPreference", Context.MODE_PRIVATE);
        }
        return instance;
    }

    public static void setData(Context context, String key, String value) {
        SharedPreferences sharedPreferences = getInstance(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void setData(Context context, String key, int value) {
        SharedPreferences sharedPreferences = getInstance(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void setData(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = getInstance(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void setData(Context context, String key, float value) {
        SharedPreferences sharedPreferences = getInstance(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static void setData(Context context, String key, long value) {
        SharedPreferences sharedPreferences = getInstance(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }
}
