package com.example.cheffy.features.home.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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
import java.util.List;


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
    ProgressBar progressBar;
    HomeRecyclerAdapter adapter;

    public HomeFragment() {
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
        progressBar = view.findViewById(R.id.progressBar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(
                getViewLifecycleOwner(), new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        requireActivity().finish();
                    }
                }
        );
        initUI(view);
        presenter = new HomePresenter(this, getMealRepositoryInstance(getContext()), getCategoryRepositoryInstance());
        presenter.handleGreetingMsg().subscribe(s -> tvGreetingMsg.setText(s));
        presenter.loadUserData();
        presenter.handleCategoryChip();
        setupChipListeners();


        return view;
    }

    private void setupChipListeners() {
        categoryChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                if (!countryChip.isChecked() && !ingredientChip.isChecked()) {
                    buttonView.setChecked(true);
                }
            } else {
                countryChip.setChecked(false);
                ingredientChip.setChecked(false);
                presenter.handleCategoryChip();
            }
        });

        countryChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                if (!categoryChip.isChecked() && !ingredientChip.isChecked()) {
                    buttonView.setChecked(true);
                }
            } else {
                categoryChip.setChecked(false);
                ingredientChip.setChecked(false);
                presenter.handleCountryChip();
            }
        });

        ingredientChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                if (!categoryChip.isChecked() && !countryChip.isChecked()) {
                    buttonView.setChecked(true);
                }
            } else {
                categoryChip.setChecked(false);
                countryChip.setChecked(false);
                presenter.handleIngredientChip();
            }
        });
    }

    private MealDataRepositoryImpl getMealRepositoryInstance(Context context) {
        return MealDataRepositoryImpl.getInstance(MealsRemoteSourceImpl.getInstance(), MealsLocalSourceImpl.getInstance(context));
    }

    private CategoryDataRepositoryImpl getCategoryRepositoryInstance() {
        return CategoryDataRepositoryImpl.getInstance(CategoriesRemoteSourceImpl.getInstance());
    }

    @Override
    public void onCardClick(Object filter) {
        String f;
        if (filter instanceof MealsResponse.Meal) {
            f = ((MealsResponse.Meal) filter).getStrArea();
            Log.i(TAG, "AREA NAME: " + f);
            presenter.filterByArea(f)
                    .subscribe((meals, throwable) -> {
                        MealsResponse.Meal[] mealsArray = meals.toArray(new MealsResponse.Meal[0]);
                        Log.i(TAG, "onCardClick: " + f);
                        Log.i(TAG, "onCardClick: " + f + " " + meals.size());
                        Navigation.findNavController(requireView()).navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment(f, mealsArray));
                    });
        } else {
            f = ((CategoryResponse.Category) filter).getStrCategory();
            Log.i(TAG, "CATEGORY NAME: " + f);
            presenter.filterByCategory(f).subscribe((meals, throwable) -> {
                MealsResponse.Meal[] mealsArray = meals.toArray(new MealsResponse.Meal[0]);
                Log.i(TAG, "onCardClick: " + f);
                Log.i(TAG, "onCardClick: " + f + " " + meals.size());
                Navigation.findNavController(requireView()).navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment(f, mealsArray));
            });
        }


    }

    @Override
    public void showLoading() {
        progressBar.setVisibility((View.VISIBLE));
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility((View.GONE));
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayUsername(String name) {
        tvUserName.setText(name);
    }

    @Override
    public void updateCategories(List<CategoryResponse.Category> categories) {
        if (adapter == null) {
            adapter = new HomeRecyclerAdapter(categories, getContext(), this);
        } else {
            adapter.updateList(categories);
        }
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void updateAreas(List<MealsResponse.Meal> areas) {
        if (adapter == null) {
            adapter = new HomeRecyclerAdapter(areas, getContext(), this);
        } else {
            adapter.updateList(areas);
        }
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void updateIngredients(List<MealsResponse.Meal> ingredients) {
        adapter.updateList(new ArrayList());

    }

    @Override
    public void clearList() {
        adapter.updateList(new ArrayList());
    }

    @Override
    public Context getViewContext() {
        return requireContext();
    }
}