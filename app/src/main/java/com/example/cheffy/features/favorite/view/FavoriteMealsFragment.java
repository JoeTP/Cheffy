package com.example.cheffy.features.favorite.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheffy.R;
import com.example.cheffy.features.favorite.contract.FavoriteContract;
import com.example.cheffy.features.favorite.presenter.FavoritePresenter;
import com.example.cheffy.features.search.view.OnMealCardClick;
import com.example.cheffy.features.search.view.SearchRecyclerAdapter;
import com.example.cheffy.repository.MealDataRepositoryImpl;
import com.example.cheffy.repository.database.meal.MealsLocalSourceImpl;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.models.plan.PlanModel;
import com.example.cheffy.repository.network.meal.MealsRemoteSourceImpl;
import com.example.cheffy.utils.AppStrings;
import com.example.cheffy.utils.ConnectionChecker;
import com.example.cheffy.utils.SharedPreferencesHelper;

import java.util.List;

public class FavoriteMealsFragment extends Fragment implements FavoriteContract.View, OnMealCardClick {

    private static final String TAG = "TEST";
    FavoritePresenter presenter;
    RecyclerView recyclerView;
    SearchRecyclerAdapter adapter;
    SharedPreferencesHelper sharedPreferencesHelper;


    public FavoriteMealsFragment() {
    }

    void initUI(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_meals, container, false);
        sharedPreferencesHelper = new SharedPreferencesHelper(getContext());
        sharedPreferencesHelper.getString(AppStrings.CURRENT_USERID, "")
                .subscribe(s -> {
                    presenter = new FavoritePresenter(this, getMealRepositoryInstance(getContext()), s);
                    presenter.getFavoriteMeals();
                }, throwable -> {
                    Log.e(TAG, "FavoritePresenter: ", throwable);
                });
        initUI(view);
        handleBackPress(view);


        return view;
    }


    void handleBackPress(View view) {
        requireActivity().getOnBackPressedDispatcher().addCallback(
                getViewLifecycleOwner(), new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        Navigation.findNavController(view).navigate(FavoriteMealsFragmentDirections.actionFavoriteMealsFragmentToHomeFragment());
                    }
                }
        );
    }

    private MealDataRepositoryImpl getMealRepositoryInstance(Context context) {
        return MealDataRepositoryImpl.getInstance(MealsRemoteSourceImpl.getInstance(), MealsLocalSourceImpl.getInstance(context));
    }

    @Override
    public void showFavoriteMeals(List<MealsResponse.Meal> meals) {
        Log.i(TAG, "showFavoriteMeals: " + meals.size());
        adapter = new SearchRecyclerAdapter(meals, getContext(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public Context returnContext() {
        return getContext();
    }


    @Override
    public void onCardClick(MealsResponse.Meal meal) {
        Navigation.findNavController(getView()).navigate(FavoriteMealsFragmentDirections.actionFavoriteMealsFragmentToMealFragment(meal));
    }

    @Override
    public void onFavoriteClick(MealsResponse.Meal meal) {
        if(ConnectionChecker.isConnected(getContext())){
        presenter.removeFromFavorite(meal);
        }else {
            Toast.makeText(getContext(), "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRemovePlanClick(PlanModel meal) {

    }
}