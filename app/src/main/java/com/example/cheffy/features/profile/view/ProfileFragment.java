package com.example.cheffy.features.profile.view;

import static com.example.cheffy.utils.AppStrings.CURRENT_USERID;
import static com.example.cheffy.utils.AppStrings.IS_LOGGED_IN_KEY;
import static com.example.cheffy.utils.AppStrings.IS_LOG_OUT_KEY;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.cheffy.R;
import com.example.cheffy.features.auth.model.User;
import com.example.cheffy.features.auth.view.AuthActivity;
import com.example.cheffy.utils.SharedPreferencesHelper;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    ConstraintLayout settingTile;
    ConstraintLayout logoutTile;
    SharedPreferencesHelper sharedPreferencesHelper;
    TextView tvUserName, tvUserEmail;
    CircleImageView ivUserImage;
    User user;

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
        user = ProfileFragmentArgs.fromBundle(getArguments()).getUser();
        setupUi(user);
        settingTile.setOnClickListener(v -> {
            Toast.makeText(getContext(), "NOT WORKING YET", Toast.LENGTH_SHORT).show();
        });

        logoutTile.setOnClickListener(v -> {
            sharedPreferencesHelper.saveBoolean(IS_LOGGED_IN_KEY, false)
                    .subscribe(() -> {
                        navigateToAuthActivity();
                        sharedPreferencesHelper.deleteKey(CURRENT_USERID).subscribe();
                    }, throwable -> {
                        Toast.makeText(getContext(), "Error during logout", Toast.LENGTH_SHORT).show();
                    });
        });

        return view;
    }

    private void setupUi(User user) {
        tvUserName.setText(user.getName());
        tvUserEmail.setText(user.getEmail());
    }


    private void initViews(View view) {
        logoutTile = view.findViewById(R.id.logoutTile);
        settingTile = view.findViewById(R.id.settingTile);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserEmail = view.findViewById(R.id.tvUserEmail);
        ivUserImage = view.findViewById(R.id.ivUserImage);

    }

    private void navigateToAuthActivity() {
        Intent intent = new Intent(requireActivity(), AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(IS_LOG_OUT_KEY, true);// Add this flag
        startActivity(intent);
        requireActivity().finish();
    }
}