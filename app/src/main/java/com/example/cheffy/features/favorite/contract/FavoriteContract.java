package com.example.cheffy.features.favorite.contract;

import com.example.cheffy.repository.models.meal.MealsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface FavoriteContract {
    interface View {

    }
    interface Presenter {
        Single<List<MealsResponse.Meal>> getFavoriteMeals();
    }

}
