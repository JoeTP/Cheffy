package com.example.cheffy.features.auth.presenter;

import static com.example.cheffy.utils.AppStrings.IS_LOGGED_IN_KEY;

import android.widget.EditText;

import com.example.cheffy.features.auth.contract.LoginContract;
import com.example.cheffy.utils.SharedPreferencesHelper;
import com.example.cheffy.utils.Validator;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private FirebaseAuth firebaseAuth;
    private SharedPreferencesHelper sharedPreferencesHelper;

    public LoginPresenter() {
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void attachView(LoginContract.View view) {
        this.view = view;
        sharedPreferencesHelper = new SharedPreferencesHelper(view.getViewContext());
    }

    @Override
    public void login(EditText email, EditText password) {
        if (!Validator.validateEmail(email) && !Validator.validatePassword(password)) return;

        view.showLoading();

        Completable.create(emitter -> {
                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),
                                    password.getText().toString())
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    emitter.onComplete();
                                    sharedPreferencesHelper.saveBoolean(IS_LOGGED_IN_KEY, true);
                                } else {
                                    emitter.onError(task.getException() != null ?
                                            task.getException() : new Exception("Login failed"));
                                }
                            });
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            view.hideLoading();
                            view.navigateToHome();
                        },
                        throwable -> {
                            view.hideLoading();
                            view.showError(throwable.getMessage());
                        }
                );
    }

    @Override
    public void handleGoogleLogin() {

    }

    @Override
    public void navigateToRegister() {
        view.navigateToRegister();
    }
}
