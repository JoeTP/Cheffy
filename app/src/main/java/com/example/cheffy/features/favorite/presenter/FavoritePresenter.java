package com.example.cheffy.features.favorite.presenter;

import android.util.Log;

import com.example.cheffy.features.favorite.contract.FavoriteContract;
import com.example.cheffy.features.meal_details.presenter.MealPresenter;
import com.example.cheffy.repository.MealDataRepositoryImpl;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.utils.AppStrings;
import com.example.cheffy.utils.SharedPreferencesHelper;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritePresenter implements FavoriteContract.Presenter {
    private static final String TAG = "TEST";

    FavoriteContract.View view;
    MealDataRepositoryImpl repo;
    String userId;

    public FavoritePresenter(FavoriteContract.View view, MealDataRepositoryImpl repo, String userId) {
        this.repo = repo;
        this.view = view;
        this.userId = userId;


    }

    @Override
    public void getFavoriteMeals() {
         repo.getMealsFromFavorites(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MealsResponse.Meal>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {}

                    @Override
                    public void onSuccess(@NonNull List<MealsResponse.Meal> meals) {
                        Log.i(TAG, "onSuccess: " + userId);
                        Log.i(TAG, "onSuccess GET MEALSE: "+ meals.size());
                        view.showFavoriteMeals(meals);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });
    }
    @Override
    public void unfavorite(String idMeal) {
        repo.removeMealFromFavorites(idMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
