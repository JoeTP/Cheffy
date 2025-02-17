package com.example.cheffy.features.auth.contract;

import android.content.Context;
import android.widget.EditText;

public interface LoginContract {
    interface View {
        void showLoading();
        void hideLoading();
        void showError(String message);
        void navigateToHome();
        void navigateToRegister();
        Context getViewContext();
    }

    interface Presenter {
        void attachView(LoginContract.View view);
        void login(EditText etEmail, EditText etPassword);
        void skipLogin();
        void handleGoogleLogin();
        void navigateToRegister();
    }
}
