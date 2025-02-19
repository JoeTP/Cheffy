package com.example.cheffy.features.auth.presenter;

import static com.example.cheffy.utils.AppStrings.CURRENT_USERID;
import static com.example.cheffy.utils.AppStrings.IS_LOGGED_IN_KEY;

import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import com.example.cheffy.R;
import com.example.cheffy.features.auth.contract.LoginContract;
import com.example.cheffy.features.auth.view.LoginFragment;
import com.example.cheffy.utils.AppStrings;
import com.example.cheffy.utils.SharedPreferencesHelper;
import com.example.cheffy.utils.Validator;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private GoogleSignInClient googleSignInClient;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1001114199026-o2nsld0knr1241poo16qj942vipctf9h.apps.googleusercontent.com")
                .requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(view.getViewContext(), gso);
    }

    @Override
    public void attachView(LoginContract.View view) {
        this.view = view;
        sharedPreferencesHelper = new SharedPreferencesHelper(view.getViewContext());
    }

    public void skipLogin() {
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
    public void navigateToRegister() {
        view.navigateToRegister();
    }


    @Override
    public void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        if (view instanceof LoginFragment) {
            ((LoginFragment) view).getGoogleSignInLauncher().launch(signInIntent);
        }
    }

    public void handleGoogleSignInResult(Intent intent) {
        GoogleSignIn.getSignedInAccountFromIntent(intent).addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                String idToken = task.getResult().getIdToken();
                firebaseAuthWithGoogle(idToken);
            } else {
                view.showError("Google Sign-In failed!");
            }
        });
    }

    public void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                view.navigateToHome();
            } else {
                view.showError(task.getException() != null ? task.getException().getMessage() : "Google Sign-In failed.");
            }
        });
    }
}
