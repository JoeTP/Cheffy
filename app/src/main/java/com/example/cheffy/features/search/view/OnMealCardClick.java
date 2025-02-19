package com.example.cheffy.features.search.view;

import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.models.plan.PlanModel;

public interface OnMealCardClick {
    void onCardClick(MealsResponse.Meal meal);
    void onFavoriteClick(MealsResponse.Meal meal);
    void onRemovePlanClick(PlanModel meal);
}
