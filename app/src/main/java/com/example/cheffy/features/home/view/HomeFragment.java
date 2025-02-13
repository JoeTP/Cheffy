package com.example.cheffy.features.home.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheffy.R;
import com.example.cheffy.features.home.contract.HomeContract;
import com.example.cheffy.features.home.presenter.HomePresenter;
import com.example.cheffy.repository.database.meal.MealsLocalSourceImpl;
import com.example.cheffy.repository.models.category.CategoryResponse;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.network.category.CategoriesRemoteSourceImpl;
import com.example.cheffy.repository.network.category.CategoryDataRepositoryImpl;
import com.example.cheffy.repository.network.meal.MealDataRepositoryImpl;
import com.example.cheffy.repository.network.meal.MealsRemoteSourceImpl;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class HomeFragment extends Fragment implements HomeContract.View, OnCardClick {

    private static final String TAG = "TEST";
    HomePresenter presenter;

    RecyclerView recyclerView;
    TextView tvGreetingMsg;
    TextView tvUserName;
    TextView tvTodaySpecial;
    ChipGroup chipGroup;
    Chip categoryChip;
    Chip countryChip;
    Chip ingredientChip;
    HomeRecyclerAdapter adapter;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initUI(view);
        presenter = new HomePresenter(this, getMealRepositoryInstance(getContext()), getCategoryRepositoryInstance());

        ///TODO: first thing u do is to make the chips cant uncheck if the others are unchecked

        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            Log.i(TAG, "GROUP LISTEN ");
            checkChipGroupChecks();
        });

        return view;
    }

    private void checkChipGroupChecks() {
        chipGroup.getCheckedChipIds().forEach(id -> {
            if (id == R.id.categoryChip) {
                subscribeCategory();
                Log.i(TAG, "subscribeCategory: ");
            } else if (id == R.id.countryChip) {
                subscribeCountry();
                Log.i(TAG, "subscribeCountry: ");
            } else if (id == R.id.ingredientChip) {
                subscribeIngredient();
                Log.i(TAG, "subscribeIngredient: ");
            }
        });
    }

    private void initUI(View view) {
        tvGreetingMsg = view.findViewById(R.id.tvGreetingMsg);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvTodaySpecial = view.findViewById(R.id.tvTodaySpecial);
        chipGroup = view.findViewById(R.id.chipGroup);
        categoryChip = view.findViewById(R.id.categoryChip);
        countryChip = view.findViewById(R.id.countryChip);
        ingredientChip = view.findViewById(R.id.ingredientChip);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    private void subscribeCategory() {
        presenter.getCategories().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categoryResponse -> {
                    if (adapter == null) {
                        adapter = new HomeRecyclerAdapter(new ArrayList<CategoryResponse.Category>(),
                                getContext(), this);
                    } else {
                        adapter.updateList(categoryResponse.getCategories());
                    }
                    recyclerView.setAdapter(adapter);
                });
    }

    private void subscribeCountry() {
//        adapter = new HomeRecyclerAdapter(new ArrayList<>(),
//                getContext(), this);
        adapter.updateList(new ArrayList<>());
//        presenter.getCountries().subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(countryResponse -> {
//                    adapter = new HomeRecyclerAdapter(new ArrayList<CountryResponse.Country>(),
//                            getContext(), this);
//                    adapter.updateList(countryResponse.getCountries());
//                    recyclerView.setAdapter(adapter);
//                    });
    }

    private void subscribeIngredient() {
//        adapter = new HomeRecyclerAdapter(new ArrayList<>(),
//                getContext(), this);
        adapter.updateList(new ArrayList<>());
//        presenter.getIngredients().subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//
//                .subscribe(ingredientResponse -> {
//                    adapter = new HomeRecyclerAdapter(new ArrayList<IngredientResponse.Ingredient>(),
//                            getContext(), this);
//                    adapter.updateList(ingredientResponse.getIngredients());
//                    recyclerView.setAdapter(adapter);
//                });
    }


    private MealDataRepositoryImpl getMealRepositoryInstance(Context context) {
        return MealDataRepositoryImpl.getInstance(MealsRemoteSourceImpl.getInstance(), MealsLocalSourceImpl.getInstance(context));
    }

    private CategoryDataRepositoryImpl getCategoryRepositoryInstance() {
        return CategoryDataRepositoryImpl.getInstance(CategoriesRemoteSourceImpl.getInstance());
    }

    @Override
    public void onCardClick(Object type) {
        if (type instanceof CategoryResponse.Category) {
            CategoryResponse.Category category = (CategoryResponse.Category) type;
            ///TODO send the type to the navigate
        } else if (type instanceof MealsResponse.Meal) {
            MealsResponse.Meal meal = (MealsResponse.Meal) type;
            ///TODO send the type to the navigate
        }
    }
}