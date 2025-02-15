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
import com.example.cheffy.utils.AppStrings;
import com.example.cheffy.utils.SharedPreferencesHelper;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
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

        getUser();
        ///TODO: first thing u do is to make the chips cant uncheck if the others are unchecked

        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            Log.i(TAG, "GROUP LISTEN ");
            checkChipGroupChecks();
        });

        return view;
    }

    private ListenerRegistration userListener;
    private Disposable userSubscription;

    private void getUser() {
        Log.i("TAG", "getUser() called");
        if (getContext() == null) return;

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        SharedPreferencesHelper sharedPreferences = new SharedPreferencesHelper(getContext());
        userSubscription =
                sharedPreferences.getString(AppStrings.CURRENT_USERID, "null")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((s, throwable) -> {
                    if (throwable != null) {
                        Log.e("SharedPreferencesError", "Error getting user ID", throwable);
                        return;
                    }
                    Log.i("TAG", "User ID retrieved: " + s);

                    userListener = firestore.collection(AppStrings.USER_COLLECTION).document(s).addSnapshotListener((value, error) -> {
                        if (error != null) {
                            Log.e("FirestoreError", "Error fetching user data", error);
                            return;
                        }
                        if (value != null && value.exists()) {
                            Log.i("TAG", "User document exists");
                            if (tvUserName != null) {
                                tvUserName.post(() -> tvUserName.setText(value.getString("name")));
                            } else {
                                Log.e("TAG", "tvUserName is null");
                            }
                        } else {
                            Log.e("TAG", "User document does not exist or is null");
                        }
                    });
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (userListener != null) {
            userListener.remove();
        }
        if (userSubscription != null) {
            userSubscription.dispose();
        }
    }

    private void checkChipGroupChecks() {
        if (chipGroup.getCheckedChipIds().isEmpty()) {
            // No chip is selected, clear the RecyclerView
            if (adapter != null) {
                adapter.updateList(new ArrayList<>());
            }
            return;
        }
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
                        adapter = new HomeRecyclerAdapter(categoryResponse.getCategories(),
                                getContext(), this);
                        recyclerView.setAdapter(adapter);
                    } else {
                        adapter.updateList(categoryResponse.getCategories());
                    }
                }, throwable -> {
                    Log.e(TAG, "Error fetching categories: ", throwable);
                });
    }

    private void subscribeCountry() {
        presenter.getAreas()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealResponse -> {
                    Log.i(TAG, "subscribeCountry: " + mealResponse.getMeals().size());
                    if (adapter == null) {
                        adapter = new HomeRecyclerAdapter(mealResponse.getMeals(),
                                getContext(), this);
                        recyclerView.setAdapter(adapter);
                    } else {
                        adapter.updateList(mealResponse.getMeals());
                    }
                }, throwable -> {
                    Log.e(TAG, "Error fetching countries: ", throwable);
                });
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