package com.example.cheffy.features.meal_details.contract;

import android.content.Context;

import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.models.plan.PlanModel;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface MealContract {
    interface View {
        void ivFavorite(Boolean isFavorite);
    }

     interface Presenter {
        Single<List<MealsResponse.Meal>> searchForMealById(String query);

        void addToFavorite(MealsResponse.Meal meal);

        void removeFromFavorite(MealsResponse.Meal meal);
        void isFavorite(String id, String idMeal);

        void insertToPlan(PlanModel plan);

        void removePlanMeal(PlanModel meal);
    }
}
