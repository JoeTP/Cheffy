package com.example.cheffy.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cheffy.models.meal.MealsResponse;
import com.example.cheffy.utils.AppStrings;


@Database(entities = {MealsResponse.Meal.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance = null;

    public abstract MealDao getMealDao();

    public static synchronized AppDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDataBase.class,
                    AppStrings.DB_NAME).build();
        }
        return instance;
    }
}

