package com.example.cheffy.features.home.view;

import android.content.Context;
import android.graphics.Outline;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.cheffy.R;
import com.example.cheffy.features.auth.model.User;
import com.example.cheffy.features.home.contract.HomeContract;
import com.example.cheffy.features.home.presenter.HomePresenter;
import com.example.cheffy.repository.database.meal.MealsLocalSourceImpl;
import com.example.cheffy.repository.models.category.CategoryResponse;
import com.example.cheffy.repository.models.ingredient.IngredientResponse;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.network.category.CategoriesRemoteSourceImpl;
import com.example.cheffy.repository.network.category.CategoryDataRepositoryImpl;
import com.example.cheffy.repository.MealDataRepositoryImpl;
import com.example.cheffy.repository.network.meal.MealsRemoteSourceImpl;
import com.example.cheffy.utils.AppStrings;
import com.example.cheffy.utils.Caching;
import com.example.cheffy.utils.ConnectionChecker;
import com.example.cheffy.utils.Flags;
import com.example.cheffy.utils.SharedPreferencesHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class HomeFragment extends Fragment implements HomeContract.View, OnCardClick {

    FirebaseDatabase firebaseDatabase;

    private static final String TAG = "TEST";
    HomePresenter presenter;
    SharedPreferencesHelper sharedPreferencesHelper;
    RecyclerView recyclerView;
    TextView tvGreetingMsg, tvUserName, tvTodaySpecial, tvSpecialMealTitle, tvSpecialMealCategory, tvSeeMore;
    Chip categoryChip, countryChip, ingredientChip;
    LottieAnimationView progressBar;
    ConstraintLayout todaySpecialCard;
    ImageView ivSpecialMealClose, ivSpecialMeal, ivUserImage, ivFlag;
    private static User user;
    HomeRecyclerAdapter adapter;
    private static MealsResponse.Meal todayMeal;

    LottieAnimationView noInternet;
    Group visGroup;
    ConnectionChecker connectionChecker;

    public HomeFragment() {
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sharedPreferencesHelper = new SharedPreferencesHelper(context);
        presenter = new HomePresenter(this, getMealRepositoryInstance(context), getCategoryRepositoryInstance());

        sharedPreferencesHelper.getInt(AppStrings.CURRENT_DAY, -1)
                .subscribe(savedDay -> {
                    Log.i(TAG, "onAttach: SAVED DAY" + savedDay);
                    int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                    if (savedDay != currentDay) {
                        presenter.todayMeal().subscribe(meals -> {
                            todayMeal = meals.get(0);
                            Log.i(TAG, "onAttach: " + todayMeal.getIdMeal());
                            updateTodaySpecialCard(todayMeal);
                            sharedPreferencesHelper.saveInt(AppStrings.CURRENT_DAY, currentDay)
                                    .subscribe();
                            sharedPreferencesHelper.saveString(AppStrings.TODAYS_MEAL_ID, todayMeal.getIdMeal()).subscribe();
                        }, throwable -> {
                            Log.e(TAG, "Error fetching today's meal", throwable);
                        });
                    } else {
                        sharedPreferencesHelper.getString(AppStrings.TODAYS_MEAL_ID, "")
                                .subscribe(savedMealId -> {
                                    Log.i(TAG, "MEAL ID TO SEARCH: " + savedMealId);
                                    presenter.searchForMealById(savedMealId).subscribe(meal -> {
//                                        Log.i(TAG, "onAttach: " + meal.size());
                                        todayMeal = meal.get(0);
                                        Log.i(TAG, "onAttach: ==>" + todayMeal.getIdMeal());
                                        updateTodaySpecialCard(todayMeal);
                                    }, throwable -> {
                                        Log.e(TAG, "Error searching for meal by ID", throwable);
                                    });
                                }, throwable -> {
                                    Log.e(TAG, "Error getting saved meal ID", throwable);
                                });
                    }
                }, throwable -> {
                    Log.e(TAG, "Error getting current day", throwable);
                });
    }

    private void initUI(View view) {
        tvGreetingMsg = view.findViewById(R.id.tvGreetingMsg);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvTodaySpecial = view.findViewById(R.id.tvTodaySpecial);
        categoryChip = view.findViewById(R.id.categoryChip);
        countryChip = view.findViewById(R.id.countryChip);
        ingredientChip = view.findViewById(R.id.ingredientChip);
        recyclerView = view.findViewById(R.id.recyclerView);
        ivUserImage = view.findViewById(R.id.ivUserImage);
        progressBar = view.findViewById(R.id.progressBar);
        todaySpecialCard = view.findViewById(R.id.todaySpecialCard);
        ivSpecialMealClose = view.findViewById(R.id.ivSpecialMealClose);
        ivSpecialMeal = view.findViewById(R.id.ivSpecialMeal);
        tvSpecialMealTitle = view.findViewById(R.id.tvSpecialMealTitle);
        tvSpecialMealCategory = view.findViewById(R.id.tvSpecialMealCategory);
        ivFlag = view.findViewById(R.id.ivFlag);
        tvSeeMore = view.findViewById(R.id.tvSeeMore);
        noInternet = view.findViewById(R.id.noInternet);
        visGroup = view.findViewById(R.id.visGroup);
        if(user == null){
            Glide.with(getContext()).load(R.drawable.profile).into(ivUserImage);
        }
        checkConnection();
    }

    @Override
    public void onResume() {
        super.onResume();
        connectionChecker.registerNetworkCallback();
    }

    @Override
    public void onPause() {
        super.onPause();
        connectionChecker.unregisterNetworkCallback();
    }

    private void checkConnection() {

        if(ConnectionChecker.isConnected(requireContext())){
            noInternet.setVisibility(View.GONE);
            visGroup.setVisibility(View.VISIBLE);
        }else{
            noInternet.setVisibility(View.VISIBLE);
            visGroup.setVisibility(View.GONE);
        }
        connectionChecker = new ConnectionChecker(getContext(), new ConnectionChecker.NetworkListener(){
            @Override
            public void onNetworkAvailable() {
                requireActivity().runOnUiThread(() -> {
                noInternet.setVisibility(View.GONE);
                visGroup.setVisibility(View.VISIBLE);
                presenter.handleCategoryChip();
                });
            }

            @Override
            public void onNetworkLost() {
                requireActivity().runOnUiThread(() -> {
                noInternet.setVisibility(View.VISIBLE);
                visGroup.setVisibility(View.GONE);
                        });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        navigateToHome();
        initUI(view);
        presenter.loadUserData();

        presenter.handleGreetingMsg().
                subscribe(s -> tvGreetingMsg.setText(s),
                        throwable -> {
                            Log.e(TAG, "Error handling greeting message", throwable);
                        });
        presenter.handleCategoryChip();
        setupChipListeners();
        tvTodaySpecial.setOnClickListener(v -> {
            toggleTodayMealCard();
            updateTodaySpecialCard(todayMeal);
        });
        ivUserImage.setOnClickListener(v -> {
                Navigation.findNavController(v).navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment(user));
        });
        ivSpecialMealClose.setOnClickListener(v -> toggleTodayMealCard());
        tvSeeMore.setOnClickListener(v -> {
            if (todayMeal != null) {
                Log.i(TAG, "tvSeeMore.setOnClickListener: " + todayMeal.getStrMeal());
                Navigation.findNavController(v).navigate(HomeFragmentDirections.actionHomeFragmentToMealFragment(todayMeal));
            } else {
                Log.i(TAG, "CANT GO STATIC NULL ");
            }
        });


        return view;
    }

    private void navigateToHome() {
        requireActivity().getOnBackPressedDispatcher().addCallback(
                getViewLifecycleOwner(), new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        requireActivity().finish();
                    }
                }
        );
    }

    private void updateTodaySpecialCard(MealsResponse.Meal meal) {
        if (todayMeal != null) {
            todayMeal = meal;
            Log.i(TAG, "updateTodaySpecialCard: " + todayMeal.getIdMeal());
            Glide.with(getContext()).load(todayMeal.getStrMealThumb()).into(ivSpecialMeal);
            tvSpecialMealTitle.setText(todayMeal.getStrMeal());
            tvSpecialMealCategory.setText(todayMeal.getStrCategory());
            Glide.with(getContext()).load(Flags.getFlagURL(todayMeal.getStrArea())).into(ivFlag);
        } else {
            Log.i(TAG, "updateTodaySpecialCard: STATIC IS NULL");
        }
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
                        if (throwable != null) {
                            Log.e(TAG, "Error filtering by area", throwable);
                            return;
                        }
                        MealsResponse.Meal[] mealsArray = meals.toArray(new MealsResponse.Meal[0]);
                        Navigation.findNavController(requireView())
                                .navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment(f + " Meals", mealsArray));
                    });
        } else if (filter instanceof CategoryResponse.Category) {
            f = ((CategoryResponse.Category) filter).getStrCategory();
            Log.i(TAG, "CATEGORY NAME: " + f);
            presenter.filterByCategory(f).subscribe((meals, throwable) -> {
                if (throwable != null) {
                    Log.e(TAG, "Error filtering by category", throwable);
                    return;
                }
                MealsResponse.Meal[] mealsArray = meals.toArray(new MealsResponse.Meal[0]);
                Navigation.findNavController(requireView())
                        .navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment("Meals with " + f, mealsArray));
            });
        } else if ( filter instanceof IngredientResponse.Meal) {
            f = ((IngredientResponse.Meal) filter).getStrIngredient();
            Log.i(TAG, "INGREDIENT NAME: " + f);
            presenter.filterByIngredient(f).subscribe((meals, throwable) -> {
                if (throwable != null) {
                    Log.e(TAG, "Error filtering by ingredient", throwable);
                    return;
                }
                MealsResponse.Meal[] mealsArray = meals.toArray(new MealsResponse.Meal[0]);
                Navigation.findNavController(requireView())
                        .navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment("Meals with " + f, mealsArray));
            });
        }
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility((View.VISIBLE));
    }

    @Override
    public void getUserData(User userData) {
        user = userData;
        Log.i(TAG, "getUserData: " + user.getId());
        Log.i(TAG, "getUserData: " + user.getName());
        Log.i(TAG, "getUserData: " + user.getEmail());
        Caching.setUser(userData);
        presenter.getRecoveredFavoriteMeals();
        presenter.getRecoveredPlanMeals();
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
        if(name == null){
        tvUserName.setText("Guest");
        }
        tvUserName.setText(name);
        if(Caching.getUser() == null)
        tvUserName.setText("Guest");
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
    public void updateIngredients(List<IngredientResponse.Meal> ingredients) {
        if (adapter == null) {
            adapter = new HomeRecyclerAdapter(ingredients, getContext(), this);
        } else {
            adapter.updateList(ingredients);
        }
        recyclerView.setAdapter(adapter);

    }

    private void toggleTodayMealCard() {
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











    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }




























}