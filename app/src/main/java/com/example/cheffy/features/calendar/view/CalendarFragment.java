package com.example.cheffy.features.calendar.view;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cheffy.R;
import com.example.cheffy.features.calendar.contract.CalendarContract;
import com.example.cheffy.features.calendar.presenter.CalendarPresenter;
import com.example.cheffy.repository.MealDataRepositoryImpl;
import com.example.cheffy.repository.database.meal.MealsLocalSourceImpl;
import com.example.cheffy.repository.models.plan.PlanModel;
import com.example.cheffy.repository.network.meal.MealsRemoteSourceImpl;
import com.example.cheffy.utils.AppStrings;
import com.example.cheffy.utils.SharedPreferencesHelper;

import java.util.List;
//import com.example.cheffy.features.FavoriteMealsFragmentDirections;

public class CalendarFragment extends Fragment implements CalendarContract.View {
    private static final String TAG = "TEST";
    SharedPreferencesHelper sharedPreferencesHelper;

    CalendarContract.Presenter presenter;

    public CalendarFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_calendar, container, false);
        presenter = new CalendarPresenter(this,getMealRepositoryInstance(getContext()));
        sharedPreferencesHelper = new SharedPreferencesHelper(getContext());
        sharedPreferencesHelper.getString(AppStrings.CURRENT_USERID, "")
                .subscribe(s -> {
                    Log.i(TAG, "onCreateView: USER ID " + s);
                    presenter.getPlanMeals(s);
                },throwable -> Log.e(TAG,
                "onCreateView: ", throwable));
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
        Log.i(TAG, "showMeals: "  + meals.size());

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

}