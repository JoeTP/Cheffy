package com.example.cheffy.features.onboard.view;

import static com.example.cheffy.utils.AppStrings.IS_BRAND_NEW_LAUNCH;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.cheffy.R;
import com.example.cheffy.utils.SharedPreferencesHelper;
import com.google.android.material.button.MaterialButton;

public class OnboardFragment extends Fragment {


    private static final String TAG = "ONBOARD";
    MaterialButton btnStart;
    SharedPreferencesHelper sharedPreferencesHelper;

    public OnboardFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sharedPreferencesHelper = new SharedPreferencesHelper(requireContext());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboard, container, false);
        btnStart = view.findViewById(R.id.btnStart);
        btnStart.setOnClickListener(v -> {
            sharedPreferencesHelper.saveBoolean(IS_BRAND_NEW_LAUNCH, false)
                    .subscribe(() -> {
                        Navigation.findNavController(v).navigate(R.id.action_onboardFragment_to_loginFragment);
                    }, throwable -> {
                        Log.e(TAG, "Error saving first-time launch state", throwable);
                    });
        });
        return view;
    }
}