package com.example.cheffy.features.auth.view;

import static com.example.cheffy.utils.AppStrings.IS_LOGGED_IN_KEY;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cheffy.MainActivity;
import com.example.cheffy.R;
import com.example.cheffy.utils.AppFunctions;
import com.example.cheffy.utils.SharedPreferencesHelper;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment {
    private FirebaseAuth mAuth;
    MaterialButton btnLogin, btnLoginGoogle;
    TextView tvRegister, tvForgotPassword;
    EditText etEmail, etPassword;
    SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sharedPreferencesHelper = new SharedPreferencesHelper(requireContext());
    }

    public LoginFragment() {}

    void initViews(View view) {
        btnLogin = view.findViewById(R.id.btnLogin);
        btnLoginGoogle = view.findViewById(R.id.btnLoginGoogle);
        tvRegister = view.findViewById(R.id.tvRegister);
        tvForgotPassword = view.findViewById(R.id.tvForgotPassword);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(view);
        mAuth = FirebaseAuth.getInstance();


        btnLogin.setOnClickListener(v -> {
//            firebaseLogin();
            AppFunctions.navigateWithIntentTo(view, MainActivity.class);
            sharedPreferencesHelper.saveBoolean(IS_LOGGED_IN_KEY, true).subscribe();
        });

        btnLoginGoogle.setOnClickListener(v -> Toast.makeText(getContext(), "NOT WORKING YET",
                Toast.LENGTH_SHORT).show());

        tvRegister.setOnClickListener(v -> AppFunctions.navigateTo(v,
                R.id.action_loginFragment_to_registerFragment));

        return view;
    }

    void firebaseLogin(View view){
        mAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString(
        )).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                AppFunctions.navigateWithIntentTo(view, MainActivity.class);
            } else {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}