package com.example.cheffy.utils;

public interface AppStrings {

    //*API
    String PHP = ".php";
    String CATEGORIES_END_POINT = "categories" + PHP;
    String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    String RANDOM_MEAL_END_POINT = "random" + PHP;
    String SEARCH_MEAL_END_POINT = "search" + PHP;
    String FILTER_END_POINT = "filter" + PHP;
    String LOOKUP_END_POINT = "lookup" + PHP;
    String ID_QUERY = "i";
    String CATEGORY_QUERY = "c";
    String AREA_QUERY = "a";
    String INGREDIENT_QUERY = "i";
    String SEARCH_QUERY = "s";
    String CATEGORY_LIST = "list" + PHP +"?" + CATEGORY_QUERY + "=list";
    String AREA_LIST = "list" + PHP +"?" + AREA_QUERY + "=list";
    String INGREDIENT_LIST = "list" + PHP +"?" + INGREDIENT_QUERY + "=list";

    //?Firebase
    String USER_COLLECTION = "users";
    String CURRENT_USERID = "current_user_id";


    //?DataBase
    String MEAL_TABLE_NAME = "meal_table";
    String PLAN_TABLE_NAME = "plan_table";
    String CATEGORY_TABLE_NAME = "category_table";
    String DB_NAME = "appdb";

    //!SharedPreference
    String SHARED_PREF_NAME = "AppPrefs";
    String IS_BRAND_NEW_LAUNCH = "IsFirstTimeLaunch";
    String IS_LOGGED_IN_KEY = "IsLoggedIn";
    String IS_LOG_OUT_KEY = "FROM_LOGOUT";
    String CURRENT_DAY = "currentDay";
    String TODAYS_MEAL_ID = "todays_meal_id";
    String TODAYS_MEAL = "";



}
