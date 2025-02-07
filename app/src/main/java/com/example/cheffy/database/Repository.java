package com.example.cheffy.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.cheffy.models.meal.MealsResponse;

import java.util.List;

public class Repository {

    private static Repository instance = null;

    private AppDataBase appDataBase;
    private MealDao mealDao;
    private LiveData<List<MealsResponse.Meal>> storedMeals;
    private Context context;

    private Repository(Context context) {
        this.context = context;
        appDataBase = AppDataBase.getInstance(context);
        mealDao = appDataBase.getMealDao();
        storedMeals = mealDao.getFavoriteMeals();

    }

    public static synchronized Repository getInstance(Context context) {
        if (instance == null) {
            instance = new Repository(context);
        }
        return instance;
    }

    public LiveData<List<MealsResponse.Meal>> getStoredMeals() {
        return storedMeals;
    }

    public void insertMeal(MealsResponse.Meal meal) {
        new Thread(() -> mealDao.addMealToFavorite(meal));
    }
    public void deleteMeal(MealsResponse.Meal meal) {
        new Thread(() -> mealDao.removeMealFromFavorite(meal));
    }
}
