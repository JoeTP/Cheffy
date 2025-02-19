package com.example.cheffy.repository.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.cheffy.repository.database.meal.MealDao;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.models.plan.PlanModel;
import com.example.cheffy.utils.AppStrings;
import com.example.cheffy.utils.Converter;


@Database(entities = {MealsResponse.Meal.class, PlanModel.class}, version = 3, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance = null;

    public abstract MealDao getMealDao();

    public static synchronized AppDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class,
                    AppStrings.DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

