package com.example.cheffy.repository.database.meal;

import androidx.lifecycle.LiveData;
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
    @Query("SELECT * FROM " + AppStrings.MEAL_TABLE_NAME)
    Observable<List<MealsResponse.Meal>> getFavoriteMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addMealToFavorite(MealsResponse.Meal meal);

    @Delete
    Completable removeMealFromFavorite(MealsResponse.Meal meal);
}
