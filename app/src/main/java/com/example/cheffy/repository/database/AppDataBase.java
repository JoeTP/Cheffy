package com.example.cheffy.repository.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.cheffy.repository.database.meal.MealDao;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.utils.AppStrings;


@Database(entities = {MealsResponse.Meal.class}, version = 1, exportSchema = false)
//@TypeConverters({Converters.class})
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance = null;

    public abstract MealDao getMealDao();

    public static synchronized AppDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class,
                    AppStrings.DB_NAME).build();
        }
        return instance;
    }
}

