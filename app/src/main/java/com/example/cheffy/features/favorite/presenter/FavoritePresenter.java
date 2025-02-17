package com.example.cheffy.features.favorite.presenter;

import com.example.cheffy.features.favorite.contract.FavoriteContract;
import com.example.cheffy.repository.MealDataRepositoryImpl;
import com.example.cheffy.repository.models.meal.MealsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class FavoritePresenter implements  FavoriteContract.Presenter {

    FavoriteContract.View view;
    MealDataRepositoryImpl repo;

    public FavoritePresenter(FavoriteContract.View view, MealDataRepositoryImpl repo) {
        this.repo = repo;
        this.view = view;
    }

    @Override
    public Single<List<MealsResponse.Meal>> getFavoriteMeals() {
        return null;
    }
}
