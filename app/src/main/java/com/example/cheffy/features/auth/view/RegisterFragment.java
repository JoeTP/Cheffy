package com.example.cheffy.features.auth.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.cheffy.MainActivity;
import com.example.cheffy.R;
import com.example.cheffy.features.auth.model.User;
import com.example.cheffy.utils.AppFunctions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth;


    public RegisterFragment() {
        // Required empty public constructor
    }


    EditText etName, etEmail, etPassword, etConfirmPassword;
    MaterialButton btnRegister, btnRegisterGoogle;
    TextView tvSignIn;

    void initViews(View view) {
        btnRegister = view.findViewById(R.id.btnRegister);
        btnRegisterGoogle = view.findViewById(R.id.btnRegisterGoogle);
        tvSignIn = view.findViewById(R.id.tvSignIn);
        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        mAuth = FirebaseAuth.getInstance();
        initViews(view);

        btnRegisterGoogle.setOnClickListener(v -> Toast.makeText(getContext(), "NOT WORKING YET", Toast.LENGTH_SHORT).show());

        btnRegister.setOnClickListener(v -> {
            ///TODO: Validation function HERE
            AppFunctions.navigateWithIntentTo(view, MainActivity.class);

//        firebaseRegister(v);
        });

        tvSignIn.setOnClickListener(v -> AppFunctions.navigateTo(v, R.id.action_registerFragment_to_loginFragment));


        return view;

    }

    private void firebaseRegister(View view) {

        User user = new User();
        mAuth.createUserWithEmailAndPassword(
                etEmail.getText().toString(), etPassword.getText().toString()
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                AppFunctions.navigateWithIntentTo(view, MainActivity.class);
                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}