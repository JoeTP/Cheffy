package com.example.cheffy.features;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.cheffy.R;
import com.example.cheffy.utils.AppFunctions;

public class FavoriteMealsFragment extends Fragment {

    public FavoriteMealsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_meals, container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(
                getViewLifecycleOwner(), new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        Navigation.findNavController(view).navigate(FavoriteMealsFragmentDirections.actionFavoriteMealsFragmentToHomeFragment());
                    }
                }
        );

        return view;
    }
}