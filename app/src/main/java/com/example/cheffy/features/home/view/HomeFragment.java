package com.example.cheffy.features.home.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
import com.example.cheffy.utils.AppStrings;
import com.example.cheffy.utils.SharedPreferencesHelper;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.Calendar;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class HomeFragment extends Fragment implements HomeContract.View, OnCardClick {

    private static final String TAG = "TEST";
    HomePresenter presenter;
    SharedPreferencesHelper sharedPreferencesHelper;
    RecyclerView recyclerView;
    TextView tvGreetingMsg;
    TextView tvUserName;
    TextView tvTodaySpecial;
    ChipGroup chipGroup;
    Chip categoryChip;
    Chip countryChip;
    Chip ingredientChip;
    ProgressBar progressBar;

    ConstraintLayout todaySpecialCard;
    ImageView ivSpecialMealClose;
    ImageView ivSpecialMeal;
    TextView tvSpecialMealTitle;
    TextView tvSpecialMealCategory;
    TextView tvSpecialMealArea;
    TextView tvSeeMore;
    HomeRecyclerAdapter adapter;
    private static MealsResponse.Meal todayMeal;

    public HomeFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sharedPreferencesHelper = new SharedPreferencesHelper(context);
        presenter = new HomePresenter(this, getMealRepositoryInstance(context), getCategoryRepositoryInstance());

        // Get the saved day from SharedPreferences
        sharedPreferencesHelper.getInt(AppStrings.CURRENT_DAY, -1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(savedDay -> {
                    int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

                    if (savedDay != currentDay) {
                        // Day has changed, fetch the new daily meal
                        presenter.todayMeal().subscribe(meals -> {
                            MealsResponse.Meal todayMeal = meals.get(0);
                            updateTodaySpecialCard(todayMeal);

                            // Save the new day and meal ID
                            sharedPreferencesHelper.saveInt(AppStrings.CURRENT_DAY, currentDay)
                                    .andThen(sharedPreferencesHelper.saveString(AppStrings.TODAYS_MEAL_ID,
                                            todayMeal.getIdMeal()))
                                    .subscribe(() -> {
                                        // Data saved successfully
                                    }, throwable -> {
                                        // Handle error
                                    });
                        });
                    } else {
                        // Day hasn't changed, use the saved meal
                        sharedPreferencesHelper.getString(AppStrings.TODAYS_MEAL_ID, "")
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(savedMealId -> {
                                    if (!savedMealId.isEmpty()) {
                                        // Fetch the saved meal details and update the UI
                                        presenter.searchForMealById(savedMealId).subscribe(meal -> {
                                            updateTodaySpecialCard(meal.get(0));
                                        });
                                    }
                                });
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
        progressBar = view.findViewById(R.id.progressBar);
        todaySpecialCard = view.findViewById(R.id.todaySpecialCard);
        ivSpecialMealClose = view.findViewById(R.id.ivSpecialMealClose);
        ivSpecialMeal = view.findViewById(R.id.ivSpecialMeal);
        tvSpecialMealTitle = view.findViewById(R.id.tvSpecialMealTitle);
        tvSpecialMealCategory = view.findViewById(R.id.tvSpecialMealCategory);
        tvSpecialMealArea = view.findViewById(R.id.tvSpecialMealArea);
        tvSeeMore = view.findViewById(R.id.tvSeeMore);
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


        tvTodaySpecial.setOnClickListener(v -> {
            // Get the saved day from SharedPreferences
            sharedPreferencesHelper.getInt(AppStrings.CURRENT_DAY, -1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(savedDay -> {
                        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

                        if (savedDay != currentDay) {
                            // Day has changed, fetch the new daily meal
                            presenter.todayMeal().subscribe(meals -> {
                                MealsResponse.Meal todayMeal = meals.get(0);
                                updateTodaySpecialCard(todayMeal);

                                // Save the new day and meal ID
                                sharedPreferencesHelper.saveInt(AppStrings.CURRENT_DAY, currentDay)
                                        .andThen(sharedPreferencesHelper.saveString(AppStrings.TODAYS_MEAL_ID,
                                                todayMeal.getIdMeal()))
                                        .subscribe(() -> {
                                            // Data saved successfully
                                        }, throwable -> {
                                            // Handle error
                                        });
                            });
                        } else {
                            // Day hasn't changed, use the saved meal
                            sharedPreferencesHelper.getString(AppStrings.TODAYS_MEAL_ID, "")
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(savedMealId -> {
                                        if (!savedMealId.isEmpty()) {
                                            // Fetch the saved meal details and update the UI
                                            presenter.searchForMealById(savedMealId).subscribe(meal -> {
                                                updateTodaySpecialCard(meal.get(0));
                                            });
                                        }
                                    });
                        }
                    });
        });

        ivSpecialMealClose.setOnClickListener(v -> handleTodaySpecialCard());
        tvTodaySpecial.setOnClickListener(v -> handleTodaySpecialCard());

        presenter.handleGreetingMsg().subscribe(s -> tvGreetingMsg.setText(s));
        presenter.loadUserData();
        presenter.handleCategoryChip();
        setupChipListeners();


        return view;
    }

    private void updateTodaySpecialCard(MealsResponse.Meal meal) {
        Glide.with(getContext()).load(meal.getStrMealThumb()).into(ivSpecialMeal);
        tvSpecialMealTitle.setText(meal.getStrMeal());
        tvSpecialMealCategory.setText(meal.getStrCategory());
        tvSpecialMealArea.setText(meal.getStrArea());
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
//                        Log.i(TAG, "onCardClick: " + f);
//                        Log.i(TAG, "onCardClick: " + f + " " + meals.size());
                        Navigation.findNavController(requireView()).navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment(f + " Meals", mealsArray));
                    });
        } else {
            f = ((CategoryResponse.Category) filter).getStrCategory();
            Log.i(TAG, "CATEGORY NAME: " + f);
            presenter.filterByCategory(f).subscribe((meals, throwable) -> {
                MealsResponse.Meal[] mealsArray = meals.toArray(new MealsResponse.Meal[0]);
//                Log.i(TAG, "onCardClick: " + f);
//                Log.i(TAG, "onCardClick: " + f + " " + meals.size());
                Navigation.findNavController(requireView()).navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment("Meals with " + f, mealsArray));
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
        if (adapter == null) {
            adapter = new HomeRecyclerAdapter(ingredients, getContext(), this);
        } else {
            adapter.updateList(ingredients);
        }
        recyclerView.setAdapter(adapter);

    }

    private void handleTodaySpecialCard() {
        if (todaySpecialCard.getVisibility() == View.GONE) {
            todaySpecialCard.setVisibility(View.VISIBLE);
        } else {
            todaySpecialCard.setVisibility(View.GONE);
        }
    }

    @Override
    public Context getViewContext() {
        return requireContext();
    }
}