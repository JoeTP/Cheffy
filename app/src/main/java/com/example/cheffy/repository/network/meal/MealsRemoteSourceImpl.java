package com.example.cheffy.repository.network.meal;

import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.network.ApiClient;
import com.example.cheffy.repository.network.NetworkCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void fetchDailyMeal(Callback<MealsResponse> callback) {
        mealsService.getDailyMeal().enqueue(callback);
    }

}

