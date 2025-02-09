package com.example.cheffy.features.home.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cheffy.R;
import com.example.cheffy.features.home.presenter.HomePresenter;
import com.example.cheffy.repository.MealDataRepositoryImpl;
import com.example.cheffy.repository.database.meal.MealsLocalSourceImpl;
import com.example.cheffy.repository.network.meal.MealsRemoteSourceImpl;


public class HomeFragment extends Fragment {

    private static final String TAG = "TEST";
    HomePresenter presenter;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home, container, false);
        Log.i(TAG, "YA 3ammmmmmmmmmm: ");
        presenter =
                new HomePresenter(MealDataRepositoryImpl.getInstance(MealsRemoteSourceImpl.getInstance(),
                        MealsLocalSourceImpl.getInstance(getContext())));
        presenter.fetchMeals().observe(getViewLifecycleOwner(), meals -> {
            Log.i(TAG, "onCreateView: " + meals.size());
        });
        return view;
    }
}