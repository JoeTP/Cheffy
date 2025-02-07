package com.example.cheffy.models.network.meal;

import com.example.cheffy.models.meal.MealsResponse;
import com.example.cheffy.models.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealsClient {
    private MealsService mealsService;
    private static MealsClient mealsClient;

    private MealsClient() {
        mealsService = ApiClient.getMealsService();
    }

    public static synchronized MealsClient getInstance() {
        if (mealsClient == null) {
            mealsClient = new MealsClient();
        }
        return mealsClient;
    }

    public void getMealsFromAPI(MealsNetworkCallback callback) {
        mealsService.getDailyMeal().enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
}

