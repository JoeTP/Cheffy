package com.example.cheffy.utils;

import android.content.Context;
import android.content.SharedPreferences;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SharedPreferencesHelper {

    private static SharedPreferences sharedPreferences = null;

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(AppStrings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public Completable saveString(String key, String value) {
        return Completable.fromAction(() -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.apply();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<String> getString(String key, String defaultValue) {
        return Single.fromCallable(() -> sharedPreferences.getString(key, defaultValue))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Completable saveInt(String key, int value) {
        return Completable.fromAction(() -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(key, value);
            editor.apply();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<Integer> getInt(String key, int defaultValue) {
        return Single.fromCallable(() -> sharedPreferences.getInt(key, defaultValue))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Completable saveBoolean(String key, boolean value) {
        return Completable.fromAction(() -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(key, value);
            editor.apply();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<Boolean> getBoolean(String key, boolean defaultValue) {
        return Single.fromCallable(() -> sharedPreferences.getBoolean(key, defaultValue))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Completable deleteKey(String key) {
        return Completable.fromAction(() -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(key);
            editor.apply();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


}