package com.example.cheffy.repository.network.meal;


import com.example.cheffy.repository.models.ingredient.IngredientResponse;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.utils.AppStrings;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsService {

    @GET(AppStrings.RANDOM_MEAL_END_POINT)
    public Single<MealsResponse> getDailyMeal();

    @GET(AppStrings.LOOKUP_END_POINT)
    public Single<MealsResponse> searchMealById(@Query(AppStrings.ID_QUERY) String mealName);
    @GET(AppStrings.LOOKUP_END_POINT)
    public Single<MealsResponse> searchMeal(@Query(AppStrings.ID_QUERY) String mealName);

    @GET(AppStrings.FILTER_END_POINT)
    public Single<MealsResponse> filterByCategory(@Query(AppStrings.CATEGORY_QUERY) String category);

    @GET(AppStrings.FILTER_END_POINT)
    public Single<MealsResponse> filterByArea(@Query(AppStrings.AREA_QUERY) String area);

    @GET(AppStrings.FILTER_END_POINT)
    public Single<MealsResponse> filterByIngredient(@Query(AppStrings.INGREDIENT_QUERY) String ingredient);

    @GET(AppStrings.AREA_LIST)
    public Single<MealsResponse> getAreas();

    @GET(AppStrings.INGREDIENT_LIST)
    public Single<IngredientResponse> getIngredients();

}
