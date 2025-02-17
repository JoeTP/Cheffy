package com.example.cheffy.repository.network.meal;

import com.example.cheffy.repository.models.ingredient.IngredientResponse;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.network.ApiClient;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Callback;

public class MealsRemoteSourceImpl implements MealsRemoteSource {
    private MealsService mealsService;
    private static MealsRemoteSourceImpl mealsClient;

    private MealsRemoteSourceImpl() {
        mealsService = ApiClient.getMealsService();
    }

    public static synchronized MealsRemoteSourceImpl getInstance() {
        if (mealsClient == null) {
            mealsClient = new MealsRemoteSourceImpl();
        }
        return mealsClient;
    }



    @Override
    public Single<MealsResponse> fetchDailyMeal() {
        return mealsService.getDailyMeal();
    }

    @Override
    public Single<MealsResponse> fetchAreas() {
        return mealsService.getAreas();
    }

    @Override
    public Single<MealsResponse> filterByCategory(String category) {
        return mealsService.filterByCategory(category);
    }

    @Override
    public Single<IngredientResponse> fetchIngredients() {
        return mealsService.getIngredients();
    }

    @Override
    public Single<MealsResponse> filterByArea(String area) {
        return mealsService.filterByArea(area);
    }

    @Override
    public Single<IngredientResponse> filterByIngredient(String ingredient) {
        return mealsService.filterByIngredient(ingredient);
    }

    @Override
    public Single<MealsResponse> searchMealById(String meal) {
        return mealsService.searchMealById(meal);
    }

}

