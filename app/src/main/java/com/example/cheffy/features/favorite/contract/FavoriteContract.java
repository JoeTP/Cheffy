package com.example.cheffy.features.favorite.contract;

import android.content.Context;

import com.example.cheffy.features.home.view.OnCardClick;
import com.example.cheffy.repository.models.meal.MealsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface FavoriteContract {
    interface View {
        void showFavoriteMeals(List<MealsResponse.Meal> meals);
        Context returnContext();

    }
    interface Presenter {
        void getFavoriteMeals();
        void removeFromFavorite(MealsResponse.Meal meal);
    }

}
