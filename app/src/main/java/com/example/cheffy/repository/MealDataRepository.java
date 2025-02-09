package com.example.cheffy.repository;

import androidx.lifecycle.LiveData;

import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.network.NetworkCallback;

import java.util.List;

import retrofit2.Callback;

public interface MealDataRepository {
    LiveData<List<MealsResponse.Meal>> getStoredFavoriteMeals();

    void insertMeal(MealsResponse.Meal meal);

    void deleteMeal(MealsResponse.Meal meal);
    void fetchMeals();

}
