package com.example.cheffy.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import androidx.navigation.Navigation;

import com.example.cheffy.R;

public abstract class AppFunctions {
     public static void navigateTo(View view, int actionId) {
        Navigation.findNavController(view).navigate(actionId);
    }
     public static void navigateWithIntentTo(View view, Class _class) {
         Intent intent = new Intent(view.getContext(), _class);
         view.getContext().startActivity(intent);
     }

    public static void myAlertDialog( Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.alert_dialog, null);

        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .setNegativeButton("Close", (dialog, which) -> {
                    dialog.dismiss();
                })
                .create();

        alertDialog.show();
    }
}
