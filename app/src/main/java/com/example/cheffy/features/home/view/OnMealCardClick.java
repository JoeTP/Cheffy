package com.example.cheffy.features.home.view;

import com.example.cheffy.repository.models.meal.MealsResponse;

public interface OnMealCardClick {
    void onCardClick(Object object);
    void onFavoriteClick(MealsResponse.Meal meal);
}
