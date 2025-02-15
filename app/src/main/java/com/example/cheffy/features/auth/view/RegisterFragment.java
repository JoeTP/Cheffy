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
import androidx.fragment.app.Fragment;

import com.example.cheffy.MainActivity;
import com.example.cheffy.R;
import com.example.cheffy.features.auth.contract.RegisterContract;
import com.example.cheffy.features.auth.presenter.RegisterPresenter;
import com.example.cheffy.utils.AppFunctions;
import com.google.android.material.button.MaterialButton;

public class RegisterFragment extends Fragment implements RegisterContract.View {

    EditText etName, etEmail, etPassword, etConfirmPassword;
    MaterialButton btnRegister, btnRegisterGoogle;
    TextView tvSignIn;
    ProgressBar progressIndicator;
    RegisterContract.Presenter presenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter = new RegisterPresenter();
        presenter.attachView(this);
    }

    public RegisterFragment() {
    }

    void initViews(View view) {
        btnRegister = view.findViewById(R.id.btnRegister);
        btnRegisterGoogle = view.findViewById(R.id.btnRegisterGoogle);
        tvSignIn = view.findViewById(R.id.tvSignIn);
        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);
        progressIndicator = view.findViewById(R.id.progressIndicator);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initViews(view);
        btnsClickListeners();

        return view;
    }

    void btnsClickListeners() {

        btnRegisterGoogle.setOnClickListener(v -> presenter.handleGoogleRegister());

        btnRegister.setOnClickListener(v -> presenter.register(etName, etEmail, etPassword, etConfirmPassword));

        tvSignIn.setOnClickListener(v -> presenter.navigateToLogin());
    }

    private void btnsEnability(boolean enable) {
        btnRegister.setEnabled(enable);
        btnRegisterGoogle.setEnabled(enable);
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
    public void navigateToLogin() {
        AppFunctions.navigateTo(getView(), R.id.action_registerFragment_to_loginFragment);
    }

    @Override
    public Context getViewContext() {
        return requireContext();
    }
}