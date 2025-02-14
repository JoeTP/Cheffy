package com.example.cheffy.repository.network.category;

import com.example.cheffy.repository.models.category.CategoryResponse;
import com.example.cheffy.repository.network.ApiClient;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
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
    public Single<CategoryResponse> getCategoriesRemote() {
        return categoriesService.getCategories();
    }
}




