package com.example.cheffy.repository.database.meal;

import androidx.lifecycle.LiveData;

import com.example.cheffy.repository.models.meal.MealsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface MealsLocalSource {
    Observable<List<MealsResponse.Meal>> getFavoriteMeals();
    void addMealToFavorite(MealsResponse.Meal meal);
    void removeMealFromFavorite(MealsResponse.Meal meal);
}
