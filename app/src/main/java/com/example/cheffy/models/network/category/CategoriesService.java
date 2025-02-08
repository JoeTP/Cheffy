package com.example.cheffy.models.network.category;

import com.example.cheffy.models.category.CategoryResponse;
import com.example.cheffy.utils.AppStrings;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriesService {
    @GET(AppStrings.CATEGORIES_END_POINT)
    Call<CategoryResponse> getCategories();

}
