package com.example.cheffy.features.meal_details.presenter;

import android.util.Log;

import com.example.cheffy.features.meal_details.contract.MealContract;
import com.example.cheffy.repository.MealDataRepository;
import com.example.cheffy.repository.models.meal.MealsResponse;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealPresenter implements MealContract.Presenter {

    MealDataRepository repo;
    MealContract.View view;

    public MealPresenter(MealContract.View view, MealDataRepository repo) {
        this.repo = repo;
        this.view = view;
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
        List<MealsResponse.Meal> favoriteMeals = repo.getMealsFromFavorites()
                .blockingFirst();
        return favoriteMeals.stream()
                .anyMatch(meal -> idMeal.equals(meal.getIdMeal()));
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
