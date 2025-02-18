package com.example.cheffy.repository.database.meal;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.models.plan.PlanModel;
import com.example.cheffy.utils.AppStrings;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MealDao {
    @Query("SELECT * FROM " + AppStrings.MEAL_TABLE_NAME + " WHERE id = :userId")
    Single<List<MealsResponse.Meal>> getFavoriteMeals(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addMeal(MealsResponse.Meal meal);

    @Query("DELETE FROM " + AppStrings.MEAL_TABLE_NAME + " WHERE isFavorite = 1 AND idMeal = :idMeal")
    Completable removeMealFromFavorite(String idMeal);

    @Query("SELECT * FROM " + AppStrings.PLAN_TABLE_NAME + " WHERE id = :userId")
    Single<List<PlanModel>> getPlanMeals(String userId);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insetPlan(PlanModel plan);
    @Delete
    Completable deletePlan(PlanModel plan);

}
