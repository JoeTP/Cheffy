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
    LiveData<List<MealsResponse.Meal>> getAllMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(MealsResponse.Meal meal);

    @Delete
    void deleteMeal(MealsResponse.Meal meal);
}
