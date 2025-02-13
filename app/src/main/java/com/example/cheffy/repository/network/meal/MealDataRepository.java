package com.example.cheffy.repository.network.meal;

import androidx.lifecycle.LiveData;

import com.example.cheffy.repository.models.meal.MealsResponse;

import java.util.List;

public interface MealDataRepository {
    LiveData<List<MealsResponse.Meal>> getStoredFavoriteMeals();

    void insertMeal(MealsResponse.Meal meal);

    void deleteMeal(MealsResponse.Meal meal);
    void fetchMeals();

}
