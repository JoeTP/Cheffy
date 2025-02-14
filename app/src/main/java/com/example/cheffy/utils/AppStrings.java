package com.example.cheffy.utils;

public interface AppStrings {

    //*API
    String PHP = ".php";
    String CATEGORIES_END_POINT = "categories" + PHP;
    String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    String RANDOM_MEAL_END_POINT = "random" + PHP;
    String SEARCH_MEAL_END_POINT = "search" + PHP;
    String FILTER_END_POINT = "filter" + PHP;
    String CATEGORY_QUERY = "c";
    String AREA_QUERY = "a";
    String INGREDIENT_QUERY = "i";
    String SEARCH_QUERY = "s";
    String CATEGORY_LIST = "list" + PHP +"?" + CATEGORY_QUERY + "=list";
    String AREA_LIST = "list" + PHP +"?" + AREA_QUERY + "=list";
    String INGREDIENT_LIST = "list" + PHP +"?" + INGREDIENT_QUERY + "=list";

    //?DataBase
    String MEAL_TABLE_NAME = "meal_table";
    String CATEGORY_TABLE_NAME = "category_table";
    String DB_NAME = "appdb";

    //!SharedPreference
    String SHARED_PREF_NAME = "AppPrefs";
    String IS_FIRST_TIME_LAUNCH_KEY = "IsFirstTimeLaunch";
    String IS_LOGGED_IN_KEY = "IsLoggedIn";


}
