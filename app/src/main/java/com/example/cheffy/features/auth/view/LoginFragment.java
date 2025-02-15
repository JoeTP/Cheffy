package com.example.cheffy.features.auth.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;

import com.example.cheffy.MainActivity;
import com.example.cheffy.R;
import com.example.cheffy.features.auth.contract.LoginContract;
import com.example.cheffy.features.auth.presenter.LoginPresenter;
import com.example.cheffy.utils.AppFunctions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;


public class LoginFragment extends Fragment implements LoginContract.View {
    private LoginContract.Presenter presenter;
    private MaterialButton btnLogin, btnLoginGoogle;
    private TextView tvRegister, tvForgotPassword;
    private EditText etEmail, etPassword;
    private ProgressBar progressIndicator;
    private Group interactionGroup;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter = new LoginPresenter();
        presenter.attachView(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(view);
        btnsClickListeners();
        return view;
    }

    private void initViews(View view) {
        btnLogin = view.findViewById(R.id.btnLogin);
        btnLoginGoogle = view.findViewById(R.id.btnLoginGoogle);
        tvRegister = view.findViewById(R.id.tvRegister);
        tvForgotPassword = view.findViewById(R.id.tvForgotPassword);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        progressIndicator = view.findViewById(R.id.progressIndicator);
        interactionGroup = view.findViewById(R.id.interactionGroup);
    }

    private void btnsClickListeners() {
        btnLogin.setOnClickListener(v -> presenter.login(etEmail, etPassword));

        btnLoginGoogle.setOnClickListener(v -> presenter.handleGoogleLogin());

        tvRegister.setOnClickListener(v -> presenter.navigateToRegister());
    }

    private void btnsEnability(boolean enable){
        btnLogin.setEnabled(enable);
        btnLoginGoogle.setEnabled(enable);
    }

    @Override
    public void showLoading() {
        progressIndicator.setVisibility(View.VISIBLE);
        btnsEnability(false);
    }

    @Override
    public void hideLoading() {
        progressIndicator.setVisibility(View.GONE);
        btnsEnability(true);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToHome() {
        AppFunctions.navigateWithIntentTo(requireView(), MainActivity.class);
    }

    @Override
    public void navigateToRegister() {
        AppFunctions.navigateTo( requireView(), R.id.action_loginFragment_to_registerFragment);
    }

    @Override
    public Context getViewContext() {
        return requireContext();
    }
}