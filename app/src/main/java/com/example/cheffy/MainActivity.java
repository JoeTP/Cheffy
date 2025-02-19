package com.example.cheffy;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.cheffy.utils.AppFunctions;
import com.example.cheffy.utils.Caching;
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
            if(Caching.getUser() == null){
                AppFunctions.myAlertDialog(this);
            }else{
            navController.navigate(R.id.calendarFragment);
            bnb.setSelectedItemId(R.id.calendarFragment);
            }
        });

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
    }
}