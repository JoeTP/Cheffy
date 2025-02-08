package com.example.cheffy.models.network.meal;


import com.example.cheffy.models.meal.MealsResponse;
import com.example.cheffy.utils.AppStrings;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsService {

    @GET(AppStrings.RANDOM_MEAL_END_POINT)
    public Call<MealsResponse> getDailyMeal();

    @GET(AppStrings.SEARCH_MEAL_END_POINT)
    public Call<MealsResponse> searchMeal(@Query(AppStrings.SEARCH_QUERY) String mealName);

    @GET(AppStrings.FILTER_END_POINT)
    public Call<MealsResponse> filterByCategory(@Query(AppStrings.CATEGORY_QUERY) String category);

    @GET(AppStrings.FILTER_END_POINT)
    public Call<MealsResponse> filterByArea(@Query(AppStrings.AREA_QUERY) String area);

    @GET(AppStrings.FILTER_END_POINT)
    public Call<MealsResponse> filterByIngredient(@Query(AppStrings.INGREDIENT_QUERY) String ingredient);

    @GET(AppStrings.AREA_LIST)
    public Call<MealsResponse> getAreas();

    @GET(AppStrings.INGREDIENT_LIST)
    public Call<MealsResponse> getIngredients();

}
