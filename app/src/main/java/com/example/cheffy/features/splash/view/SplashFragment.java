package com.example.cheffy.features.splash.view;

import static com.example.cheffy.utils.AppStrings.IS_BRAND_NEW_LAUNCH;
import static com.example.cheffy.utils.AppStrings.IS_LOGGED_IN_KEY;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.cheffy.MainActivity;
import com.example.cheffy.R;
import com.example.cheffy.utils.AppFunctions;
import com.example.cheffy.utils.SharedPreferencesHelper;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SplashFragment extends Fragment {

    private static final String TAG = "SPLASH";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

}