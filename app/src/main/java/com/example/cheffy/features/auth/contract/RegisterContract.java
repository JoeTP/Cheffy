package com.example.cheffy.features.auth.contract;

import com.example.cheffy.features.auth.model.User;

public interface RegisterContract {
    interface View {
        void showProgressBar();

        void hideProgressBar();

        void showError(String message);

        void navigateToHome();
    }

    interface Presenter {
        void register(User user);
    }
}
