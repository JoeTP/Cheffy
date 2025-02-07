package com.example.cheffy.models.network.meal;

import com.example.cheffy.models.meal.MealsResponse;

import java.util.List;

public interface MealsNetworkCallback {
    void onSuccess(List<MealsResponse.Meal> meals);
    void onFailure(String message);
}
