package com.example.cheffy.features.meal_details.presenter;

import com.example.cheffy.features.meal_details.contract.MealContract;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.network.meal.MealDataRepository;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
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
