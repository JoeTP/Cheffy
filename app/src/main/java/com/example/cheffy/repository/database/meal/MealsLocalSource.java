package com.example.cheffy.repository.database.meal;

import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.models.plan.PlanModel;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface MealsLocalSource {
    Single<List<MealsResponse.Meal>> getFavoriteMeals(String id);
    Completable addMeal(MealsResponse.Meal meal);
    Completable removeMealFromFavorite(String idMeal);

    Completable insetPlan(PlanModel plan);
    Completable deletePlan(PlanModel plan);
    Single<List<PlanModel>> getPlanMeals(String userId);
    Completable removeFavoriteMeal(MealsResponse.Meal meal);
    Single<Boolean> isInFavourite(String id , String idMeal);
    Completable recoverFavoriteMeals(List<MealsResponse.Meal> favouriteMeals);
}
