package com.example.cheffy.features.home.presenter;

import android.util.Log;

import com.example.cheffy.features.home.contract.HomeContract;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.network.category.CategoryDataRepositoryImpl;
import com.example.cheffy.repository.network.meal.MealDataRepositoryImpl;
import com.example.cheffy.utils.AppStrings;
import com.example.cheffy.utils.SharedPreferencesHelper;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenter implements HomeContract.Presenter {

    private static final String TAG = "TEST";
    private final HomeContract.View view;
    private final CategoryDataRepositoryImpl categoryRepo;
    private final MealDataRepositoryImpl mealRepo;
    private final SharedPreferencesHelper sharedPreferences;
    private final FirebaseFirestore firestore;

    public HomePresenter(HomeContract.View view, MealDataRepositoryImpl mealRepo, CategoryDataRepositoryImpl categoryRepo) {
        this.view = view;
        this.mealRepo = mealRepo;
        this.categoryRepo = categoryRepo;
        sharedPreferences = new SharedPreferencesHelper(view.getViewContext());
        firestore = FirebaseFirestore.getInstance();
    }


    @Override
    public Observable<String> handleGreetingMsg() {
        return Observable.interval(1, TimeUnit.HOURS)
                .startWithItem(0L)
                .map(tick -> {
                    int hour  = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                    if (hour >= 0 && hour < 12) {
                        return "Good Morning";
                    } else if (hour >= 12 && hour < 17) {
                        return "Good Afternoon";
                    } else {
                        return "Good Evening";
                    }
                })
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void loadUserData() {
        sharedPreferences.getString(AppStrings.CURRENT_USERID, "Guest")
                .flatMap(userId ->
                        Single.create(emitter ->
                                firestore.collection(AppStrings.USER_COLLECTION)
                                        .document(userId)
                                        .get()
                                        .addOnSuccessListener(userData -> {
                                            ///TODO: remember to get the backedup data
                                            if (userData != null && userData.exists()) {
                                                String name = userData.getString("name");
                                                emitter.onSuccess(name != null ? name : "Guest");
                                            } else {
                                                emitter.onSuccess("Guest");
                                            }
                                        })
                        )
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userName -> {
                            view.displayUsername((String) userName);
                        },
                        throwable -> {
                            Log.e(TAG, "Error loading user data", throwable);
                            view.displayUsername("Guest");
                        }
                );
    }

    @Override
    public void handleCategoryChip() {
        view.showLoading();
        categoryRepo.getCategoriesRemote()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categories -> {
                            view.hideLoading();
                            view.updateCategories(categories.getCategories());
                        },
                        throwable -> {
                            view.hideLoading();
                            view.showError("Failed to load categories");
                            Log.e(TAG, "Error loading categories", throwable);
                        }
                );
    }

    @Override
    public void handleCountryChip() {
        view.showLoading();
        mealRepo.getAreasRemote()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        areas -> {
                            view.hideLoading();
                            Log.i(TAG, "handleIngredientChip: " + areas.getMeals().size());
                            view.updateAreas(areas.getMeals());
                        },
                        throwable -> {
                            view.hideLoading();
                            view.showError("Failed to load areas");
                            Log.e(TAG, "Error loading areas", throwable);
                        }
                );
    }

    @Override
    public void handleIngredientChip() {
        view.showLoading();
        mealRepo.getIngredientsRemote()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ingredients -> {
                            view.hideLoading();
                            Log.i(TAG, "handleIngredientChip: " + ingredients.getMeals().size());
                            view.updateIngredients(ingredients.getMeals());
                        },
                        throwable -> {
                            view.hideLoading();
                            view.showError("Failed to load ingredients");
                            Log.e(TAG, "Error loading ingredients", throwable);
                        }
                );
    }

    @Override
    public Single<List<MealsResponse.Meal>> filterByCategory(String category) {
        return mealRepo.getFilterByCategory(category)
                .map(mealsResponse -> mealsResponse.getMeals())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<List<MealsResponse.Meal>> filterByArea(String filter) {
        return mealRepo.getFilterByArea((filter))
                .map(mealsResponse -> mealsResponse.getMeals())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<List<MealsResponse.Meal>> todayMeal() {
        return mealRepo.getDailyMealRemote()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(mealsResponse -> mealsResponse.getMeals());
    }

    @Override
    public Single<List<MealsResponse.Meal>> searchForMealById(String id) {
        return mealRepo.searchMealById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(mealsResponse ->
                        mealsResponse.getMeals().stream()
                                .filter(meal -> meal.getIdMeal().equals(id))
                                .collect(Collectors.toList())
                );
    }
}
