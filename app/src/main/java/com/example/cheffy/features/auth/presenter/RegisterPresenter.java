package com.example.cheffy.features.auth.presenter;

import static com.example.cheffy.utils.AppStrings.IS_LOGGED_IN_KEY;
import static com.example.cheffy.utils.AppStrings.CURRENT_USERID;

import android.util.Log;
import android.widget.EditText;

import com.example.cheffy.features.auth.contract.RegisterContract;
import com.example.cheffy.features.auth.model.User;
import com.example.cheffy.utils.AppStrings;
import com.example.cheffy.utils.SharedPreferencesHelper;
import com.example.cheffy.utils.Validator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.View view;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private SharedPreferencesHelper sharedPreferencesHelper;

    public RegisterPresenter() {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void attachView(RegisterContract.View view) {
        this.view = view;
        sharedPreferencesHelper = new SharedPreferencesHelper(view.getViewContext());
    }

    @Override
    public void register(EditText etName, EditText etEmail, EditText etPassword, EditText etConfirmPassword) {
        if (!Validator.validateEmpty(etName) || !Validator.validateEmail(etEmail) ||
                !Validator.validatePassword(etPassword) || !Validator.validateMismatch(etPassword,
                etConfirmPassword)) return;
        view.showLoading();
        Completable.create(emitter -> {
                    firebaseAuth.createUserWithEmailAndPassword(etEmail.getText().toString(),
                            etPassword.getText().toString()).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onComplete();
                            String userId = firebaseAuth.getCurrentUser().getUid();
                            User user = new User(etName.getText().toString(), etEmail.getText().toString());
                            firestore.collection(AppStrings.USER_COLLECTION).document(userId).set(user.toMap());
                            sharedPreferencesHelper.saveBoolean(IS_LOGGED_IN_KEY, true).subscribe();
                            Log.i("TAG", "register: USER ID: " + userId);
                            sharedPreferencesHelper.saveString(CURRENT_USERID, userId).subscribe();
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
