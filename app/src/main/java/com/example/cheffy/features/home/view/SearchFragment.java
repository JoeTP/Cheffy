package com.example.cheffy.features.home.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.cheffy.R;
import com.example.cheffy.repository.models.meal.MealsResponse;

public class SearchFragment extends Fragment implements OnMealCardClick {

    private static final String TAG = "TEST";
    TextView tvTitle;

    public SearchFragment() {
    }

    void initUI(View view) {
        tvTitle = view.findViewById(R.id.tvTitle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initUI(view);
        tvTitle.setText(SearchFragmentArgs.fromBundle(getArguments()).getFilter());
        int listSize = SearchFragmentArgs.fromBundle(getArguments()).getMeals().length;
        Log.i(TAG, "RECEIVER LIST " + listSize);
        return view;
    }

    @Override
    public void onCardClick(MealsResponse.Meal meal) {

    }

    @Override
    public void onFavoriteClick(MealsResponse.Meal meal) {

    }
}