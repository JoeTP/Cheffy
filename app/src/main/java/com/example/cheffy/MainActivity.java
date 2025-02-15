package com.example.cheffy;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.cheffy.utils.AppFunctions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bnb;
    FloatingActionButton fabCalendar;
    private NavController navController;

    private void initViews() {
        bnb = findViewById(R.id.bnb);
        fabCalendar = findViewById(R.id.fabCalendar);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.home_nav_host_fragment);
        navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(bnb, navController);
        fabCalendar.setOnClickListener(v -> {
            navController.navigate(R.id.calendarFragment);
            bnb.setSelectedItemId(R.id.calendarFragment);
        });
//        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {
//            if(navDestination.getId() == R.id.favoriteMealsFragment){
//
//            }
//        });

        bnb.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.homeFragment) {
                navController.navigate(R.id.homeFragment);
                return true;
            } else if (itemId == R.id.favoriteMealsFragment) {
                navController.navigate(R.id.favoriteMealsFragment);
                return true;
            }
            return false;
        });



//
//        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
//            if (destination.getId() == R.id.calendarFragment) {
//                bnb.setSelectedItemId(R.id.calendarFragment);
//            }
//        });
    }
}