package com.example.cheffy.repository.database.meal;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.cheffy.repository.database.AppDataBase;
import com.example.cheffy.repository.models.meal.MealsResponse;

import java.util.List;

public class MealsLocalSourceImpl implements MealsLocalSource {

    private static MealsLocalSourceImpl instance = null;
    MealDao mealDao;
    AppDataBase appDataBase;
    LiveData<List<MealsResponse.Meal>> storedFavoriteMeals;
    Context context;

    private MealsLocalSourceImpl(Context context) {
        this.context = context;
        appDataBase = appDataBase.getInstance(context);
        mealDao = appDataBase.getMealDao();
//        storedFavoriteMeals = mealDao.getFavoriteMeals();
    }

    public static synchronized MealsLocalSourceImpl getInstance(Context context) {
        if (instance == null) {
            instance = new MealsLocalSourceImpl(context);
        }
        return instance;
    }


    @Override
    public LiveData<List<MealsResponse.Meal>> getFavoriteMeals() {
        return storedFavoriteMeals;
    }

    @Override
    public void addMealToFavorite(MealsResponse.Meal meal) {
        new Thread(() -> mealDao.addMealToFavorite(meal)).start();
    }

    @Override
    public void removeMealFromFavorite(MealsResponse.Meal meal) {
        new Thread(() -> mealDao.removeMealFromFavorite(meal)).start();
    }
}
