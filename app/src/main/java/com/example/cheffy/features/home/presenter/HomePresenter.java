package com.example.cheffy.features.home.presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cheffy.features.auth.model.User;
import com.example.cheffy.features.home.contract.HomeContract;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.models.plan.PlanModel;
import com.example.cheffy.repository.network.category.CategoryDataRepositoryImpl;
import com.example.cheffy.repository.MealDataRepositoryImpl;
import com.example.cheffy.utils.AppStrings;
import com.example.cheffy.utils.Caching;
import com.example.cheffy.utils.SharedPreferencesHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
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
    DatabaseReference dbRef;
    public HomePresenter(HomeContract.View view, MealDataRepositoryImpl mealRepo, CategoryDataRepositoryImpl categoryRepo) {
        this.view = view;
        this.mealRepo = mealRepo;
        this.categoryRepo = categoryRepo;
        sharedPreferences = new SharedPreferencesHelper(view.getViewContext());
        firestore = FirebaseFirestore.getInstance();

        dbRef = FirebaseDatabase.getInstance().getReference()
                .child("root")
                .child("users");
    }


    @Override
    public Observable<String> handleGreetingMsg() {
        return Observable.interval(1, TimeUnit.HOURS)
                .startWithItem(0L)
                .map(tick -> {
                    int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
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
        sharedPreferences.getString(AppStrings.CURRENT_USERID, "")
                .flatMap(userId -> Single.create(emitter -> {
                    Log.i(TAG, "loadUserData ===>: " + userId);
                    if(!userId.isEmpty()){
                        firestore.collection(AppStrings.USER_COLLECTION)
                                .document(userId)
                                .get()
                                .addOnSuccessListener(userData -> {
                                    if (userData != null && userData.exists()) {
                                        User user = new User();
                                        user.setId(userId);
                                        user.setName(userData.getString("name"));
                                        user.setEmail(userData.getString("email"));
                                        Log.i(TAG, "loadUserData: " + user.getId());
                                        emitter.onSuccess(user);
                                    }
                                });
                    }

                }))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                            User userData = (User) user;
                            Log.i(TAG, "loadUserData>>>>>>>>: " + userData.getId());
                            view.displayUsername(userData.getName());
                            view.getUserData(userData);
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
    public Single<List<MealsResponse.Meal>> filterByIngredient(String filter) {
        return mealRepo.getFilterByIngredient(filter)
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

    @Override
    public void getRecoveredFavoriteMeals() {
        dbRef.child(Caching.getUser().getId())
                .child("favorite")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<MealsResponse.Meal> recoveredMeals = new ArrayList<>();
                        Log.i(TAG, "onDataChange: " + snapshot.getValue());
                        for(DataSnapshot ds : snapshot.getChildren()){
                            MealsResponse.Meal meal = ds.getValue(MealsResponse.Meal.class);
                            recoveredMeals.add(meal);
                        }
                        setFavoriteToDataBase(recoveredMeals);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public void getRecoveredPlanMeals() {
        dbRef.child(Caching.getUser().getId())
                .child("plan")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<PlanModel> recoveredMeals = new ArrayList<>();
                        Log.i(TAG, "onDataChange: " + snapshot.getValue());
                        for(DataSnapshot ds : snapshot.getChildren()){
                            PlanModel meal = ds.getValue(PlanModel.class);
                            recoveredMeals.add(meal);
                        }
                        for ( PlanModel meal : recoveredMeals){
                            Log.i(TAG, "getRecoveredPlanMeals++++++++++: " + meal.getDate());
                        }
                        setPlanToDataBase(recoveredMeals);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }




    void setFavoriteToDataBase(List<MealsResponse.Meal> meals){
        mealRepo.recoverFavoriteMeals(meals)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {},throwable -> Log.i(TAG, "setFavoriteToDataBase: " + throwable));
    }

    void setPlanToDataBase(List<PlanModel> meals){
        mealRepo.recoverPlanMeals(meals)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {},throwable -> Log.i(TAG, "setPlanoDataBase: " + throwable));
    }

}
