package com.example.cheffy.repository.network.meal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cheffy.repository.database.meal.MealsLocalSourceImpl;
import com.example.cheffy.repository.models.meal.MealsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

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

    }

    @Override
    public Single<MealsResponse> getAreasRemote() {
        return mealsRemoteSource.fetchAreas();
    }

    @Override
    public Single<MealsResponse> getFilterByCategory(String category) {
        return mealsRemoteSource.filterByCategory(category);
    }

    @Override
    public Single<MealsResponse> getFilterByArea(String area) {
        return mealsRemoteSource.filterByArea(area);
    }

    @Override
    public Single<MealsResponse> getFilterByIngredient(String ingredient) {
        return mealsRemoteSource.filterByIngredient(ingredient);
    }

    @Override
    public Single<MealsResponse> searchMealById(String meal) {
        return mealsRemoteSource.searchMealById(meal);
    }

}
