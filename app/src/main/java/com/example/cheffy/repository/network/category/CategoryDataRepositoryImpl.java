package com.example.cheffy.repository.network.category;

import com.example.cheffy.repository.models.category.CategoryResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class CategoryDataRepositoryImpl implements CategoryDataRepository {

    private static CategoryDataRepositoryImpl instance = null;

    CategoriesRemoteSourceImpl categoriesRemoteSource;
//    CategoriesLocalSourceImpl categoriesLocalSource;

    private CategoryDataRepositoryImpl(CategoriesRemoteSourceImpl categoriesRemoteSource) {
        this.categoriesRemoteSource = categoriesRemoteSource;
    }

    public static synchronized CategoryDataRepositoryImpl getInstance(CategoriesRemoteSourceImpl categoriesRemoteSource) {
        if (instance == null) {
            instance = new CategoryDataRepositoryImpl(categoriesRemoteSource);
        }
        return instance;
    }

    @Override
    public Single<CategoryResponse> getCategoriesRemote() {
        return categoriesRemoteSource.getCategoriesRemote();
    }
}
