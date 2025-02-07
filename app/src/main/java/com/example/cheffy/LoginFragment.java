package com.example.cheffy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment {
    private FirebaseAuth mAuth;
    MaterialButton btnLogin, btnLoginGoogle;
    TextView tvRegister;
    EditText etEmail, etPassword;

    public LoginFragment() {
        // Required empty public constructor
    }

    void initViews(View view){
        btnLogin = view.findViewById(R.id.btnLogin);
        btnLoginGoogle = view.findViewById(R.id.btnLoginGoogle);
        tvRegister = view.findViewById(R.id.tvRegister);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mAuth = FirebaseAuth.getInstance();
        initViews(view);
        tvRegister.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment));

        return view;
    }
}