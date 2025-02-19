package com.example.cheffy.features.search.presenter;

import com.example.cheffy.features.search.contract.SearchContract;
import com.example.cheffy.repository.MealDataRepository;

public class SearchPresenter implements SearchContract.Presenter {
    MealDataRepository repo;
    SearchContract.View view;

    public SearchPresenter(SearchContract.View view, MealDataRepository repo) {
        this.repo = repo;
        this.view = view;
    }

//    @Override
//    public Single<List<MealsResponse.Meal>> searchForMeal(String query) {
//        return repo.searchMeal(query).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(mealsResponse -> mealsResponse.getMeals());
//    }
}
