package com.example.cheffy.features.calendar.view;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cheffy.R;
import com.example.cheffy.features.FavoriteMealsFragmentDirections;

public class CalendarFragment extends Fragment {



    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_calendar, container, false);

        requireActivity().getOnBackPressedDispatcher().addCallback(
                getViewLifecycleOwner(), new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        Navigation.findNavController(view).navigate(CalendarFragmentDirections.actionCalendarFragmentToHomeFragment());
                    }
                }
        );

        return view;
    }
}