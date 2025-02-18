package com.example.cheffy.repository;

import com.example.cheffy.repository.models.ingredient.IngredientResponse;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.models.plan.PlanModel;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface MealDataRepository {

    //!Local
    Single<List<MealsResponse.Meal>> getMealsFromFavorites(String id);
    Completable insertMeal(MealsResponse.Meal meal);
    Completable removeMealFromFavorites(String idMeal);
    Completable insetPlan(PlanModel plan);
    Completable deletePlan(PlanModel plan);
    Single<List<PlanModel>> getPlanMeals(String userId);

    //!Remote
    Single<MealsResponse> getAreasRemote();
    Single<IngredientResponse> getIngredientsRemote();
    Single<MealsResponse> getDailyMealRemote();
    Single<MealsResponse> getFilterByCategory(String category);
    Single<MealsResponse> getFilterByArea(String area);
    Single<MealsResponse> getFilterByIngredient(String ingredient);
    Single<MealsResponse> searchMealById(String meal);

}
