package com.example.cheffy.features.auth.contract;

import android.content.Context;
import android.widget.EditText;

public interface RegisterContract {
    interface View {
        void showLoading();

        void hideLoading();

        void showError(String message);

        void navigateToHome();
        void navigateToLogin();
        Context getViewContext();

    }

    interface Presenter {
        void attachView(RegisterContract.View view);
        void register(EditText etName, EditText etEmail, EditText etPassword, EditText etConfirmPassword);
        void handleGoogleRegister();
        void navigateToLogin();
    }
}
