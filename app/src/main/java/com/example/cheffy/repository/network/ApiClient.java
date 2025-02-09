package com.example.cheffy.repository.network;

import com.example.cheffy.repository.network.category.CategoriesService;
import com.example.cheffy.repository.network.meal.MealsService;
import com.example.cheffy.utils.AppStrings;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class ApiClient {
    private static Retrofit retrofit;

    private static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppStrings.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static CategoriesService getCategoriesService() {
        return getRetrofitInstance().create(CategoriesService.class);
    }

    public static MealsService getMealsService() {
        return getRetrofitInstance().create(MealsService.class);
    }
}
