package com.example.cheffy.repository.database.meal;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.utils.AppStrings;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface MealDao {
    @Query("SELECT * FROM " + AppStrings.MEAL_TABLE_NAME + " WHERE isFavorite = 1")
    Observable<List<MealsResponse.Meal>> getFavoriteMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addMeal(MealsResponse.Meal meal);

    @Query("DELETE FROM " + AppStrings.MEAL_TABLE_NAME + " WHERE isFavorite = 1 AND idMeal = :idMeal")
    Completable removeMealFromFavorite(String idMeal);

    @Query("SELECT * FROM " + AppStrings.MEAL_TABLE_NAME + " WHERE isFavorite = 0")
    Observable<List<MealsResponse.Meal>> getPlanMeals();

}
