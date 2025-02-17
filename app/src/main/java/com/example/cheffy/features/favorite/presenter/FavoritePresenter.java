package com.example.cheffy.features.favorite.presenter;

import android.util.Log;

import com.example.cheffy.features.favorite.contract.FavoriteContract;
import com.example.cheffy.features.meal_details.presenter.MealPresenter;
import com.example.cheffy.repository.MealDataRepositoryImpl;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritePresenter implements FavoriteContract.Presenter {
    private static final String TAG = "TEST";

    FavoriteContract.View view;
    MealDataRepositoryImpl repo;

    public FavoritePresenter(FavoriteContract.View view, MealDataRepositoryImpl repo) {
        this.repo = repo;
        this.view = view;
    }

    @Override
    public void getFavoriteMeals() {
         repo.getMealsFromFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {
                    Log.i(TAG, "getFavoriteMeals: " + meals.size());
                    view.showFavoriteMeals(meals);
                },throwable -> {
                    Log.i(TAG, "getFavoriteMeals: " + throwable.getMessage());
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
