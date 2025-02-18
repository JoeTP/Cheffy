package com.example.cheffy.features.auth.presenter;

import static com.example.cheffy.utils.AppStrings.IS_LOGGED_IN_KEY;
import static com.example.cheffy.utils.AppStrings.CURRENT_USERID;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.EditText;

import com.example.cheffy.features.auth.contract.LoginContract;
import com.example.cheffy.utils.AppStrings;
import com.example.cheffy.utils.SharedPreferencesHelper;
import com.example.cheffy.utils.Validator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private SharedPreferencesHelper sharedPreferencesHelper;

    public LoginPresenter() {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void attachView(LoginContract.View view) {
        this.view = view;
        sharedPreferencesHelper = new SharedPreferencesHelper(view.getViewContext());
    }

    public void skipLogin(){
        sharedPreferencesHelper.saveBoolean(IS_LOGGED_IN_KEY, true).subscribe();
//        sharedPreferencesHelper.saveString(CURRENT_USERID, "null").subscribe();
        view.navigateToHome();
    }
    @Override
    public void login(EditText email, EditText password) {
        if (!Validator.validateEmail(email) || !Validator.validatePassword(password)) return;

        view.showLoading();

        Completable.create(emitter -> {
                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),
                                    password.getText().toString())
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    emitter.onComplete();
                                    String userId = firebaseAuth.getCurrentUser().getUid();
                                    firestore.collection(AppStrings.USER_COLLECTION).document(userId)
                                            .addSnapshotListener((value, error) -> {
                                                if (error != null) {
                                                    emitter.onError(new Exception("User Data not found"));
                                                }
                                            });
                                    sharedPreferencesHelper.saveBoolean(IS_LOGGED_IN_KEY, true).subscribe();
                                    Log.i("TAG", "login presenter user ID: " + userId);
                                    sharedPreferencesHelper.saveString(CURRENT_USERID, userId).subscribe();
                                } else {
                                    emitter.onError(task.getException());
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
