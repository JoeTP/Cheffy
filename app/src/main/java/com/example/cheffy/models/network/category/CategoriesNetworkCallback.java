package com.example.cheffy.models.network.category;

import com.example.cheffy.models.category.CategoryResponse;
import com.example.cheffy.models.meal.MealsResponse;

import java.util.List;

public interface CategoriesNetworkCallback {
    void onSuccess(List<CategoryResponse.Category> meals);
    void onFailure(String message);
}
