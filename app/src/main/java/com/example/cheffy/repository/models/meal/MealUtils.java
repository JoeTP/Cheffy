package com.example.cheffy.repository.models.meal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class MealUtils {
    public static List<String> getIngredients(MealsResponse.Meal meal) {
        List<String> ingredients = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            try {
                Field field = MealsResponse.Meal.class.getDeclaredField("strIngredient" + i);
                field.setAccessible(true);
                String ingredient = (String) field.get(meal);
                if (ingredient != null && !ingredient.isEmpty()) {
                    ingredients.add(ingredient);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return ingredients;
    }

    public static List<String> getMeasures(MealsResponse.Meal meal) {
        List<String> measures = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            try {
                Field field = MealsResponse.Meal.class.getDeclaredField("strMeasure" + i);
                field.setAccessible(true);
                String measure = (String) field.get(meal);
                if (measure != null && !measure.isEmpty()) {
                    measures.add(measure);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return measures;
    }
}
