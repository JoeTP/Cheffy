package com.example.cheffy.repository.network.meal;

import androidx.lifecycle.LiveData;

import com.example.cheffy.repository.models.meal.MealsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface MealDataRepository {
    LiveData<List<MealsResponse.Meal>> getStoredFavoriteMeals();

    void insertMeal(MealsResponse.Meal meal);

    void deleteMeal(MealsResponse.Meal meal);
    void fetchMeals();
    Single<MealsResponse> getAreasRemote();
    Single<MealsResponse> getFilterByCategory(String category);
    Single<MealsResponse> getFilterByArea(String area);
    Single<MealsResponse> getFilterByIngredient(String ingredient);

}
