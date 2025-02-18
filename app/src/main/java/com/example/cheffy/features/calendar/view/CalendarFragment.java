package com.example.cheffy.features.calendar.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheffy.R;
import com.example.cheffy.features.calendar.contract.CalendarContract;
import com.example.cheffy.features.calendar.presenter.CalendarPresenter;
import com.example.cheffy.features.search.view.OnMealCardClick;
import com.example.cheffy.repository.MealDataRepositoryImpl;
import com.example.cheffy.repository.database.meal.MealsLocalSourceImpl;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.models.plan.PlanModel;
import com.example.cheffy.repository.network.meal.MealsRemoteSourceImpl;
import com.example.cheffy.utils.Caching;
import com.example.cheffy.utils.SharedPreferencesHelper;

import java.util.Calendar;
import java.util.List;
//import com.example.cheffy.features.FavoriteMealsFragmentDirections;

public class CalendarFragment extends Fragment implements CalendarContract.View, OnMealCardClick {
    private static final String TAG = "TEST";
    SharedPreferencesHelper sharedPreferencesHelper;

    CalendarContract.Presenter presenter;

    CalendarView calendarView;
    RecyclerView recyclerView;
    CalendarAdapter adapter;

    private void initUI(View view) {
        calendarView = view.findViewById(R.id.calendarView);
        recyclerView = view.findViewById(R.id.recyclerView);

    }

    public CalendarFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        initUI(view);
        presenter = new CalendarPresenter(this, getMealRepositoryInstance(getContext()));
        presenter.getPlanMeals(Caching.getUser().getId());


        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
            Log.d(TAG, "Selected Date: " + selectedDate);
            presenter.filterMealsByDate(selectedDate);
        });
        navigateToHome(view);


        return view;
    }



    private void navigateToHome(View view) {
        requireActivity().getOnBackPressedDispatcher().addCallback(
                getViewLifecycleOwner(), new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        Navigation.findNavController(view).navigate(CalendarFragmentDirections.actionCalendarFragmentToHomeFragment());
                    }
                }
        );
    }

    @Override
    public void showMeals(List<PlanModel> meals) {
        Log.i(TAG, "showMeals: " + meals.size());
        adapter = new CalendarAdapter(meals, getContext(), this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    private MealDataRepositoryImpl getMealRepositoryInstance(Context context) {
        return MealDataRepositoryImpl.getInstance(MealsRemoteSourceImpl.getInstance(), MealsLocalSourceImpl.getInstance(context));
    }

    @Override
    public void onCardClick(MealsResponse.Meal meal) {
        Navigation.findNavController(getView()).navigate(CalendarFragmentDirections.actionCalendarFragmentToMealFragment(meal));

    }

    @Override
    public void onFavoriteClick(MealsResponse.Meal meal) {

    }
}