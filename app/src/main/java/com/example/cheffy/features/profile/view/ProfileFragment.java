package com.example.cheffy.features.profile.view;

import static com.example.cheffy.utils.AppStrings.IS_LOGGED_IN_KEY;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.cheffy.R;
import com.example.cheffy.features.auth.view.AuthActivity;
import com.example.cheffy.utils.AppStrings;
import com.example.cheffy.utils.SharedPreferencesHelper;

public class ProfileFragment extends Fragment {

    private ConstraintLayout tileAccountSetting;
    private ConstraintLayout tileLogout;
    private SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sharedPreferencesHelper = new SharedPreferencesHelper(context);
    }

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initViews(view);

        tileAccountSetting.setOnClickListener(v -> {
            Toast.makeText(getContext(), "NOT WORKING YET", Toast.LENGTH_SHORT).show();
        });

        tileLogout.setOnClickListener(v -> {
            sharedPreferencesHelper.saveBoolean(IS_LOGGED_IN_KEY, false)
                    .subscribe(() -> {
                        navigateToAuthActivity();
                    }, throwable -> {
                        Toast.makeText(getContext(), "Error during logout", Toast.LENGTH_SHORT).show();
                    });
        });

        return view;
    }

    private void initViews(View view) {
        tileLogout = view.findViewById(R.id.tileLogout);
        tileAccountSetting = view.findViewById(R.id.tileAccountSetting);
    }

    private void navigateToAuthActivity() {
        Intent intent = new Intent(requireActivity(), AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("FROM_LOGOUT", true);  // Add this flag
        startActivity(intent);
        requireActivity().finish();
    }
}