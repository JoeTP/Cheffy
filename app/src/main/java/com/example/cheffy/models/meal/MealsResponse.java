package com.example.cheffy.models.meal;

import java.util.List;

public class MealsResponse {
    List<Meal> meals;

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    @Override
    public String toString() {
        return "MealsResponse{" +
                "meals=" + meals +
                '}';
    }

    public static class Meal {
        String idMeal;
        String strMeal;
        String strDrinkAlternate;
        String strCategory;
        String strArea;
        String strInstructions;
        String strMealThumb;
        String strTags;
        String strYoutube;
        List<String> strIngredient;
        List<String> strMeasure;
        List<String> strSource;

        public String getIdMeal() {
            return idMeal;
        }

        public void setIdMeal(String idMeal) {
            this.idMeal = idMeal;
        }

        public String getStrMeal() {
            return strMeal;
        }

        public void setStrMeal(String strMeal) {
            this.strMeal = strMeal;
        }

        public String getStrDrinkAlternate() {
            return strDrinkAlternate;
        }

        public void setStrDrinkAlternate(String strDrinkAlternate) {
            this.strDrinkAlternate = strDrinkAlternate;
        }

        public String getStrCategory() {
            return strCategory;
        }

        public void setStrCategory(String strCategory) {
            this.strCategory = strCategory;
        }

        public String getStrArea() {
            return strArea;
        }

        public void setStrArea(String strArea) {
            this.strArea = strArea;
        }

        public String getStrInstructions() {
            return strInstructions;
        }

        public void setStrInstructions(String strInstructions) {
            this.strInstructions = strInstructions;
        }

        public String getStrMealThumb() {
            return strMealThumb;
        }

        public void setStrMealThumb(String strMealThumb) {
            this.strMealThumb = strMealThumb;
        }

        public String getStrTags() {
            return strTags;
        }

        public void setStrTags(String strTags) {
            this.strTags = strTags;
        }

        public String getStrYoutube() {
            return strYoutube;
        }

        public void setStrYoutube(String strYoutube) {
            this.strYoutube = strYoutube;
        }

        public List<String> getStrIngredient() {
            return strIngredient;
        }

        public void setStrIngredient(List<String> strIngredient) {
            this.strIngredient = strIngredient;
        }

        public List<String> getStrMeasure() {
            return strMeasure;
        }

        public void setStrMeasure(List<String> strMeasure) {
            this.strMeasure = strMeasure;
        }

        public List<String> getStrSource() {
            return strSource;
        }

        public void setStrSource(List<String> strSource) {
            this.strSource = strSource;
        }
    }
}
