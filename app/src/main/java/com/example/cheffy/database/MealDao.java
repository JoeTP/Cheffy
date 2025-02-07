package com.example.cheffy.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cheffy.models.meal.MealsResponse;
import com.example.cheffy.utils.AppStrings;

import java.util.List;

@Dao
public interface MealDao {
    @Query("SELECT * FROM " + AppStrings.TABLE_NAME)
    LiveData<List<MealsResponse.Meal>> getFavoriteMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMealToFavorite(MealsResponse.Meal meal);

    @Delete
    void removeMealFromFavorite(MealsResponse.Meal meal);
}
