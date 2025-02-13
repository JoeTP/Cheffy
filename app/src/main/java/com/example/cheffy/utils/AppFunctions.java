package com.example.cheffy.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.navigation.Navigation;

public abstract class AppFunctions {
     public static void navigateTo(View view, int actionId) {
        Navigation.findNavController(view).navigate(actionId);
    }
     public static void navigateWithIntentTo(View view, Class _class) {
         Intent intent = new Intent(view.getContext(), _class);
         view.getContext().startActivity(intent);
//         ((Activity) view.getContext()).finish();
     }
}
