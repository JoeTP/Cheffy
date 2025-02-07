package com.example.cheffy.database;

import android.content.Context;

import com.example.cheffy.models.meal.MealsResponse;

@Database(entities = {MealsResponse.Meal.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance = null;

    public abstract ProductDao getProductDao();

    public static synchronized AppDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDataBase.class,
                    AppStrings.DATABASE_NAME).build();
        }
        return instance;
    }
}

