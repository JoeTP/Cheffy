package com.example.cheffy.features.meal_details.contract;

import com.example.cheffy.repository.models.meal.MealsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface MealContract {
    interface View {
//        void setUI(MealsResponse.Meal meal);
    }
    interface Presenter {
        public Single<List<MealsResponse.Meal>> searchForMealById(String query);
    }
}
