package com.example.cheffy.repository.network.category;

import com.example.cheffy.repository.network.NetworkCallback;
import com.example.cheffy.repository.models.category.CategoryResponse;
import com.example.cheffy.repository.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesRemoteSourceImpl implements CategoriesRemoteSource {
    private CategoriesService categoriesService;
    private static CategoriesRemoteSourceImpl categoryClient;

    private CategoriesRemoteSourceImpl() {
        categoriesService = ApiClient.getCategoriesService();
    }

    public static synchronized CategoriesRemoteSourceImpl getInstance() {
        if (categoryClient == null) {
            categoryClient = new CategoriesRemoteSourceImpl();
        }
        return categoryClient;
    }

    @Override
    public void getCategoriesFromAPI(NetworkCallback callback) {
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




