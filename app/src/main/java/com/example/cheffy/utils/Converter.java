package com.example.cheffy.utils;

import androidx.room.TypeConverter;

import com.example.cheffy.repository.models.meal.MealsResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Converter {

    private static Gson gson = new Gson();

    @TypeConverter
    public static String fromMeal(MealsResponse.Meal meal) {
        return meal == null ? null : gson.toJson(meal);
    }

    @TypeConverter
    public static MealsResponse.Meal toMeal(String mealString) {
        return mealString == null ? null : gson.fromJson(mealString, new TypeToken<MealsResponse.Meal>() {}.getType());
    }
}
