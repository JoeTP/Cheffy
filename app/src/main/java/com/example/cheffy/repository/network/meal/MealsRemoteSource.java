package com.example.cheffy.repository.network.meal;

import com.example.cheffy.repository.models.meal.MealsResponse;

import retrofit2.Callback;

public interface MealsRemoteSource {
    void fetchDailyMeal(Callback<MealsResponse> callback);
}
