package com.example.cheffy.utils;

import android.view.View;

import androidx.navigation.Navigation;

public abstract class AppFunctions {
     public static void navigateTo(View view, int actionId) {
        Navigation.findNavController(view).navigate(actionId);
    }
}
