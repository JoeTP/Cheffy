package com.example.cheffy.features.search.contract;

import com.example.cheffy.repository.models.meal.MealsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface SearchContract {
    interface View {
        void showLoading();
        void hideLoading();
        void showError(String message);
    }
    interface Presenter {
        Single<List<MealsResponse.Meal>> searchForMeal(String query);
    }
}
