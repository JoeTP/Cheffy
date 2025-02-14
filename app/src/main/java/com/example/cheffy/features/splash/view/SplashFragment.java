package com.example.cheffy.features.splash.view;

import static com.example.cheffy.utils.AppStrings.IS_FIRST_TIME_LAUNCH_KEY;
import static com.example.cheffy.utils.AppStrings.IS_LOGGED_IN_KEY;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cheffy.MainActivity;
import com.example.cheffy.R;
import com.example.cheffy.utils.AppFunctions;
import com.example.cheffy.utils.SharedPreferencesHelper;

public class SplashFragment extends Fragment {

    private static final String TAG = "SPLASH";
    SharedPreferencesHelper sharedPreferencesHelper;

    public SplashFragment() {}


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sharedPreferencesHelper = new SharedPreferencesHelper(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                sharedPreferencesHelper.getBoolean(IS_FIRST_TIME_LAUNCH_KEY, true)
                        .subscribe(isFirstTime -> {
                            if (isFirstTime) {
                                Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_onboardFragment);
                            } else {
                                sharedPreferencesHelper.getBoolean(IS_LOGGED_IN_KEY, false).subscribe(isLoggedIn -> {
                                    if(isLoggedIn){
                                        AppFunctions.navigateWithIntentTo(view, MainActivity.class);
                                    }else {
                                        Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_loginFragment);
                                    }
                                });
                            }
                        }, throwable -> {
                            Log.e(TAG, "Error checking first-time launch state", throwable);
                        });
            }
        }, 2000);
        return view;
    }
}