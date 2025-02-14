package com.example.cheffy.repository.network.category;

import com.example.cheffy.repository.models.category.CategoryResponse;
import com.example.cheffy.utils.AppStrings;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriesService {
    @GET(AppStrings.CATEGORIES_END_POINT)
    Single<CategoryResponse> getCategories();

}
