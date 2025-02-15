package com.example.cheffy.features.home.contract;

import android.content.Context;
import android.widget.TextView;

import com.example.cheffy.repository.models.category.CategoryResponse;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.google.type.TimeOfDay;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface HomeContract {
    interface View {
        void showLoading();
        void hideLoading();
        void showError(String message);
        void displayUsername(String name);
        void updateCategories(List<CategoryResponse.Category> categories);
        void updateAreas(List<MealsResponse.Meal> areas);
        void updateIngredients(List<MealsResponse.Meal> ingredients);
        void clearList();
        Context getViewContext();
    }
    interface Presenter {
        Observable<String> handleGreetingMsg();
        void loadUserData();
        void handleCategoryChip();
        void handleCountryChip();
        void handleIngredientChip();
        void onCardClicked(Object item);

    }
}
