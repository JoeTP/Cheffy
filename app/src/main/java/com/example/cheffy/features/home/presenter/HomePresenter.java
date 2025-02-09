package com.example.cheffy.features.home.presenter;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.cheffy.repository.MealDataRepositoryImpl;
import com.example.cheffy.repository.models.meal.MealsResponse;

import java.util.List;

public class HomePresenter {
    MealDataRepositoryImpl repository;

    public HomePresenter(MealDataRepositoryImpl repository) {
        this.repository = repository;
    }

    public LiveData<List<MealsResponse.Meal>> fetchMeals(){
        Log.i("TEST", "fetchMeals: FROM PRESENTER");
        repository.fetchMeals();
        return repository.mealList;
    }
}
