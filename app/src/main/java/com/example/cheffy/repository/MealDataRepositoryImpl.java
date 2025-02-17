package com.example.cheffy.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.cheffy.repository.database.meal.MealsLocalSourceImpl;
import com.example.cheffy.repository.models.ingredient.IngredientResponse;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.network.meal.MealsRemoteSourceImpl;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
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
    public Observable<List<MealsResponse.Meal>> getMealsFromFavorites() {
        return mealsLocalSource.getFavoriteMeals();
    }

    @Override
    public Observable<List<MealsResponse.Meal>> getMealsFromPlan() {
        return mealsLocalSource.getPlanMeals();
    }

    @Override
    public Completable insertMeal(MealsResponse.Meal meal) {
        return mealsLocalSource.addMeal(meal);
    }

    @Override
    public Completable removeMealFromFavorites(String idMeal) {
        return mealsLocalSource.removeMealFromFavorite(idMeal);
    }

    //!Network
    @Override
    public Single<MealsResponse> getAreasRemote() {
        return mealsRemoteSource.fetchAreas();
    }

    @Override
    public Single<IngredientResponse> getIngredientsRemote() {
        return mealsRemoteSource.fetchIngredients();
    }

    @Override
    public Single<MealsResponse> getDailyMealRemote() {
        return mealsRemoteSource.fetchDailyMeal();
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
