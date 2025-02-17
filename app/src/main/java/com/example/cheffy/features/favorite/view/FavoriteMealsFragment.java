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

import com.example.cheffy.R;
import com.example.cheffy.features.favorite.contract.FavoriteContract;
import com.example.cheffy.features.favorite.presenter.FavoritePresenter;
import com.example.cheffy.features.favorite.view.FavoriteMealsFragmentDirections;
import com.example.cheffy.repository.MealDataRepositoryImpl;
import com.example.cheffy.repository.database.meal.MealsLocalSourceImpl;
import com.example.cheffy.repository.network.meal.MealsRemoteSourceImpl;

public class FavoriteMealsFragment extends Fragment implements FavoriteContract.View {

    FavoritePresenter presenter;

    public FavoriteMealsFragment() {
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


    private MealDataRepositoryImpl getMealRepositoryInstance(Context context) {
        return MealDataRepositoryImpl.getInstance(MealsRemoteSourceImpl.getInstance(), MealsLocalSourceImpl.getInstance(context));
    }
}