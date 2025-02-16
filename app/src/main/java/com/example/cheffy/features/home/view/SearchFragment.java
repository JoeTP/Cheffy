package com.example.cheffy.features.home.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheffy.R;
import com.example.cheffy.repository.models.meal.MealsResponse;

import java.util.List;

public class SearchFragment extends Fragment implements OnMealCardClick {

    private static final String TAG = "TEST";
    TextView tvTitle;
    EditText etSearch;
    RecyclerView recyclerView;
    SearchRecyclerAdapter adapter;

    public SearchFragment() {}

    void initUI(View view) {
        tvTitle = view.findViewById(R.id.tvTitle);
        etSearch = view.findViewById(R.id.etSearch);
        recyclerView = view.findViewById(R.id.recyclerView);
        List<MealsResponse.Meal> meals = List.of(SearchFragmentArgs.fromBundle(getArguments()).getMeals());
        adapter = new SearchRecyclerAdapter(meals, getContext(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initUI(view);
        tvTitle.setText(SearchFragmentArgs.fromBundle(getArguments()).getFilter());


        return view;
    }

    @Override
    public void onCardClick(MealsResponse.Meal meal) {
        Log.i(TAG, "onCardClick: " + meal.getStrMeal());
        Navigation.findNavController(requireView()).navigate(SearchFragmentDirections.actionSearchFragmentToMealFragment());
    }

    @Override
    public void onFavoriteClick(MealsResponse.Meal meal) {
        Log.i(TAG, "onFavoriteClick: ");
    }
}