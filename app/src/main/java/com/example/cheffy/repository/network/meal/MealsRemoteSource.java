package com.example.cheffy.repository.network.meal;

import com.example.cheffy.repository.models.meal.MealsResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Callback;

public interface MealsRemoteSource {
    ///TODO: Fix this
    void fetchDailyMeal(Callback<MealsResponse> callback);

    Single<MealsResponse> fetchAreas();
    Single<MealsResponse> filterByCategory(String category);
    Single<MealsResponse> filterByArea(String area);
    Single<MealsResponse> filterByIngredient(String ingredient);

    Single<MealsResponse> searchMealById(String meal);
}
