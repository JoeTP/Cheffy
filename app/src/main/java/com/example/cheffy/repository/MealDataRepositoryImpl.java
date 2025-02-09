package com.example.cheffy.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cheffy.repository.database.meal.MealsLocalSourceImpl;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.network.NetworkCallback;
import com.example.cheffy.repository.network.meal.MealsRemoteSourceImpl;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealDataRepositoryImpl implements MealDataRepository {

    private static MealDataRepositoryImpl instance = null;

    MealsRemoteSourceImpl mealsRemoteSource;
    MealsLocalSourceImpl mealsLocalSource;
    public MutableLiveData<List<MealsResponse.Meal>> mealList;

    private MealDataRepositoryImpl(MealsRemoteSourceImpl mealsRemoteSource, MealsLocalSourceImpl mealsLocalSource) {
        this.mealsLocalSource = mealsLocalSource;
        this.mealsRemoteSource = mealsRemoteSource;
        mealList = new MutableLiveData<>();
    }

    public static synchronized MealDataRepositoryImpl getInstance(MealsRemoteSourceImpl mealsRemoteSource, MealsLocalSourceImpl mealsLocalSource) {
        if (instance == null) {
            instance = new MealDataRepositoryImpl(mealsRemoteSource, mealsLocalSource);
        }
        return instance;
    }

    //!Local
    @Override
    public LiveData<List<MealsResponse.Meal>> getStoredFavoriteMeals() {
        return mealsLocalSource.getFavoriteMeals();
    }

    @Override
    public void insertMeal(MealsResponse.Meal meal) {
        new Thread(() -> mealsLocalSource.addMealToFavorite(meal)).start();
    }

    @Override
    public void deleteMeal(MealsResponse.Meal meal) {
        new Thread(() -> mealsLocalSource.removeMealFromFavorite(meal)).start();
    }

    //!Network
    @Override
    public void fetchMeals() {
        mealsRemoteSource.fetchDailyMeal(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                if (response.isSuccessful()) {
                    Log.i("TEST", "fetchMeals: FROM REPO" + response.body());

                }
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable t) {
                Log.i("TEST", "onFailure: " + t.getMessage().toString());
            }
        });
    }

}
