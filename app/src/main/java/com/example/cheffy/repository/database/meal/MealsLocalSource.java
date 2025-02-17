package com.example.cheffy.repository.database.meal;

import com.example.cheffy.repository.models.meal.MealsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public interface MealsLocalSource {
    Observable<List<MealsResponse.Meal>> getFavoriteMeals();
    Completable addMeal(MealsResponse.Meal meal);
    Completable removeMealFromFavorite(String idMeal);
    Observable<List<MealsResponse.Meal>> getPlanMeals();
}
