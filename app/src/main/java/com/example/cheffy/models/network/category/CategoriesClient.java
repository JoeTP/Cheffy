package com.example.cheffy.models.network.category;

import com.example.cheffy.models.category.CategoryResponse;
import com.example.cheffy.models.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesClient {
    private CategoriesService categoriesService;
    private static CategoriesClient categoryClient;

    private CategoriesClient() {
        categoriesService = ApiClient.getCategoriesService();
    }

    public static synchronized CategoriesClient getInstance() {
        if (categoryClient == null) {
            categoryClient = new CategoriesClient();
        }
        return categoryClient;
    }

    public void getCategoriesFromAPI(CategoriesNetworkCallback callback) {
        categoriesService.getCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body().getCategories());
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
}
