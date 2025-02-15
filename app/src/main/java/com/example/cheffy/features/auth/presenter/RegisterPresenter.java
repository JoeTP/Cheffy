package com.example.cheffy.features.auth.presenter;

import static com.example.cheffy.utils.AppStrings.IS_LOGGED_IN_KEY;

import android.widget.EditText;

import com.example.cheffy.features.auth.contract.RegisterContract;
import com.example.cheffy.utils.SharedPreferencesHelper;
import com.example.cheffy.utils.Validator;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.View view;
    private FirebaseAuth firebaseAuth;
    private SharedPreferencesHelper sharedPreferencesHelper;

    public RegisterPresenter() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void attachView(RegisterContract.View view) {
        this.view = view;
        sharedPreferencesHelper = new SharedPreferencesHelper(view.getViewContext());
    }

    @Override
    public void register(EditText etName, EditText etEmail, EditText etPassword, EditText etConfirmPassword) {
        if (!Validator.validateEmpty(etName) && !Validator.validateEmail(etEmail) &&
                !Validator.validatePassword(etPassword) && !Validator.validateMismatch(etPassword,
                etConfirmPassword)) return;
        view.showLoading();

        Completable.create(emitter -> {
                    firebaseAuth.createUserWithEmailAndPassword(etEmail.getText().toString(),
                            etPassword.getText().toString()).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onComplete();
                            sharedPreferencesHelper.saveBoolean(IS_LOGGED_IN_KEY, true);
                        } else {
                            emitter.onError(task.getException() != null ? task.getException() : new Exception("Register failed"));
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
                });

    }

    @Override
    public void handleGoogleRegister() {

    }

    @Override
    public void navigateToLogin() {
        view.navigateToLogin();
    }
}
