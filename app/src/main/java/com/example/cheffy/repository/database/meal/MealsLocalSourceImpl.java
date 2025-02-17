package com.example.cheffy.repository.database.meal;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.cheffy.repository.database.AppDataBase;
import com.example.cheffy.repository.models.meal.MealsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

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
    public Observable<List<MealsResponse.Meal>> getFavoriteMeals() {
        return mealDao.getFavoriteMeals();
    }

    @Override
    public Completable addMeal(MealsResponse.Meal meal) {
        return mealDao.addMeal(meal);
    }

    @Override
    public Completable removeMealFromFavorite(String idMeal) {
        return mealDao.removeMealFromFavorite(idMeal);
    }

    @Override
    public Observable<List<MealsResponse.Meal>> getPlanMeals() {
        return mealDao.getPlanMeals();
    }
}
