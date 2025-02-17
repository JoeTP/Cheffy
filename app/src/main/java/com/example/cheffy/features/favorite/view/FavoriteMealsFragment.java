package com.example.cheffy.features.favorite.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheffy.R;
import com.example.cheffy.features.favorite.contract.FavoriteContract;
import com.example.cheffy.features.favorite.presenter.FavoritePresenter;
import com.example.cheffy.features.favorite.view.FavoriteMealsFragmentDirections;
import com.example.cheffy.features.home.view.OnCardClick;
import com.example.cheffy.features.search.view.SearchRecyclerAdapter;
import com.example.cheffy.repository.MealDataRepositoryImpl;
import com.example.cheffy.repository.database.meal.MealsLocalSourceImpl;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.network.meal.MealsRemoteSourceImpl;

import java.util.List;

public class FavoriteMealsFragment extends Fragment implements FavoriteContract.View , OnCardClick {

    FavoritePresenter presenter;
    RecyclerView recyclerView;
    SearchRecyclerAdapter adapter;

    public FavoriteMealsFragment() {}

    void initUI(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter = new FavoritePresenter(this, getMealRepositoryInstance(context));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_meals, container, false);
        initUI(view);
        handleBackPress(view);




        return view;
    }


    void handleBackPress( View view) {
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
        adapter = new SearchRecyclerAdapter(meals, getContext(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCardClick(Object object) {
        MealsResponse.Meal meal = (MealsResponse.Meal) object;
        Navigation.findNavController(getView()).navigate(FavoriteMealsFragmentDirections.actionFavoriteMealsFragmentToMealFragment(meal));
    }
}