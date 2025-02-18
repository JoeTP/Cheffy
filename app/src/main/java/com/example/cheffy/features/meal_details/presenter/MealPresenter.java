package com.example.cheffy.features.meal_details.presenter;

import android.content.Context;
import android.util.Log;

import com.example.cheffy.features.meal_details.contract.MealContract;
import com.example.cheffy.repository.MealDataRepository;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.models.plan.PlanModel;
import com.example.cheffy.utils.AppStrings;
import com.example.cheffy.utils.SharedPreferencesHelper;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealPresenter implements MealContract.Presenter {

    private static final String TAG = "TEST";
    MealDataRepository repo;
    MealContract.View view;
    SharedPreferencesHelper sharedPreferencesHelper;
    String userId;

    public MealPresenter(MealContract.View view, MealDataRepository repo, Context context) {
        this.repo = repo;
        this.view = view;
        sharedPreferencesHelper = new SharedPreferencesHelper(context);
        sharedPreferencesHelper.getString(AppStrings.CURRENT_USERID, "").subscribe(
                s -> {
                    userId = s;
                }, throwable -> {
                    Log.e(TAG, "MealPresenter: ", throwable);
                }
        );
    }

    @Override
    public Single<List<MealsResponse.Meal>> searchForMealById(String id) {
        return repo.searchMealById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(mealsResponse ->
                        mealsResponse.getMeals().stream()
                                .filter(meal -> meal.getIdMeal().equals(id))
                                .collect(Collectors.toList())
                );
    }

    @Override
    public void addToFavorite(MealsResponse.Meal meal) {
        meal.setId(userId);
        Log.i(TAG, "addToFavorite: " + userId);
        repo.insertMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("TEST", "onError: " + e.getMessage());
                    }
                });

    }

    @Override
    public void unfavorite(String idMeal) {
        repo.removeMealFromFavorites(idMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("TEST", "onError: " + e.getMessage());
                    }
                });
    }

    @Override
    public boolean isFavorite(String idMeal) {
        return false;
    }

    @Override
    public void insertToPlan(PlanModel plan) {
        plan.setId(userId);
        repo.insetPlan(plan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                        }, throwable ->
                                Log.e(TAG, "insertToPlan: ", throwable)
                );
    }

//    @Override
//    public Single<List<MealsResponse.Meal>> searchForMeal(String query) {
//        return repo.searchMealById(query).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(mealsResponse -> mealsResponse.getMeals())
//                .map(meals -> meals.stream()
//                        .filter(meal -> meal.getStrMeal()
//                                .toLowerCase()
//                                .contains(query.toLowerCase()))
//                        .collect(Collectors.toList()));
//    }
}
