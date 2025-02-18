package com.example.cheffy.repository.database.meal;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.cheffy.repository.database.AppDataBase;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.models.plan.PlanModel;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class MealsLocalSourceImpl implements MealsLocalSource {

    private static MealsLocalSourceImpl instance = null;
    MealDao mealDao;
    AppDataBase appDataBase;
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
    public Single<List<MealsResponse.Meal>> getFavoriteMeals(String id) {
        Log.i("TEST", "getFavoriteMeals LOCAL SOURCE: " + id);
        return mealDao.getFavoriteMeals(id);
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
    public Completable insetPlan(PlanModel plan) {
        return mealDao.insetPlan(plan);
    }

    @Override
    public Completable deletePlan(PlanModel plan) {
        return mealDao.deletePlan(plan);
    }

    @Override
    public Single<List<PlanModel>> getPlanMeals(String userId) {
        return mealDao.getPlanMeals(userId);
    }

    @Override
    public Completable removeFavoriteMeal(MealsResponse.Meal meal) {
        return mealDao.removeFavoriteMeal(meal);
    }

    @Override
    public Single<Boolean> isInFavourite(String id, String idMeal) {
        return mealDao.isInFavourite(id,idMeal);
    }

    @Override
    public Completable recoverFavoriteMeals(List<MealsResponse.Meal> favouriteMeals) {
        return mealDao.recoverFavoriteMeals(favouriteMeals);
    }
}
