package com.example.cheffy.features.home.contract;

import com.example.cheffy.repository.models.category.CategoryResponse;
import com.example.cheffy.repository.models.meal.MealsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface HomeContract {
    interface View {
//        void showMeals(List<MealsResponse.Meal> meals);
//        void showCategories(Single<CategoryResponse> obsCategory);
    }
    interface Presenter {
        void getMeals();
        Single<CategoryResponse>  getCategories();
        Single<MealsResponse> getAreas();
    }
}
