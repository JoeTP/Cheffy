package com.example.cheffy.features.auth.contract;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;

public interface LoginContract {
    interface View {
        void showLoading();
        void hideLoading();
        void showError(String message);
        void navigateToHome();
        void navigateToRegister();
        ActivityResultLauncher<Intent> getGoogleSignInLauncher();

        Context getViewContext();
    }

    interface Presenter {
        void attachView(LoginContract.View view);
        void login(EditText etEmail, EditText etPassword);
        void skipLogin();
        void handleGoogleSignInResult(Intent data);
        void signInWithGoogle();
        void navigateToRegister();
    }
}
