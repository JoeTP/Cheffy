package com.example.cheffy.features.auth.view;

import static com.example.cheffy.utils.AppStrings.IS_BRAND_NEW_LAUNCH;
import static com.example.cheffy.utils.AppStrings.IS_LOGGED_IN_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cheffy.MainActivity;
import com.example.cheffy.R;
import com.example.cheffy.utils.SharedPreferencesHelper;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AuthActivity extends AppCompatActivity {

    private static final String TAG = "AUTH ACTIVITY";
    private SharedPreferencesHelper sharedPreferencesHelper;
    private NavController navController;
    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        sharedPreferencesHelper = new SharedPreferencesHelper(this);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.auth_nav_host_fragment);
        navController = navHostFragment.getNavController();

        if (getIntent().getBooleanExtra("FROM_LOGOUT", false)) {
            navController.navigate(R.id.loginFragment);
        } else {
            disposables.add(
                    Completable.complete()
                            .delay(2, TimeUnit.SECONDS)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(() -> checkAuthenticationState(),
                                    throwable -> Log.e(TAG, "Error during delay", throwable))
            );
        }

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (navController.getCurrentDestination().getId() == R.id.loginFragment) {
                    finish();
                } else {
                    navController.navigateUp();
                }
            }
        });

    }

    private void checkAuthenticationState() {
        disposables.add(
                sharedPreferencesHelper.getBoolean(IS_BRAND_NEW_LAUNCH, true)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(isFirstTime -> {
                            if (isFirstTime) {
                                navController.navigate(R.id.action_splashFragment_to_onboardFragment);
                            } else {
                                checkLoginState();
                            }
                        }, throwable -> Log.e(TAG, "Error checking first-time launch state", throwable))
        );
    }

    private void checkLoginState() {
        disposables.add(
                sharedPreferencesHelper.getBoolean(IS_LOGGED_IN_KEY, false)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(isLoggedIn -> {
                            if (isLoggedIn) {
                                Intent intent = new Intent(this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                navController.navigate(R.id.action_splashFragment_to_loginFragment);
                            }
                        }, throwable -> Log.e(TAG, "Error checking login state", throwable))
        );
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
}