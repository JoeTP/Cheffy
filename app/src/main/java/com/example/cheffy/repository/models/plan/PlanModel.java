package com.example.cheffy.repository.models.plan;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.utils.AppStrings;

@Entity(tableName = AppStrings.PLAN_TABLE_NAME, primaryKeys = {"id", "idMeal", "date"})
public class PlanModel {
    @NonNull
    String id;
    @NonNull
    String idMeal;
    @NonNull
    String date;
    MealsResponse.Meal meal;

    public PlanModel() {
    }

    public PlanModel(@NonNull String id, @NonNull String date, MealsResponse.Meal meal) {
        this.id = id;
        this.idMeal = meal.getIdMeal();
        this.date = date;
        this.meal = meal;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(@NonNull String idMeal) {
        this.idMeal = idMeal;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public MealsResponse.Meal getMeal() {
        return meal;
    }

    public void setMeal(MealsResponse.Meal meal) {
        this.meal = meal;
    }
}
