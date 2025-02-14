package com.example.cheffy.features.home.presenter;

import com.example.cheffy.features.home.contract.HomeContract;
import com.example.cheffy.features.home.view.OnCardClick;
import com.example.cheffy.repository.models.category.CategoryResponse;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.network.category.CategoryDataRepositoryImpl;
import com.example.cheffy.repository.network.meal.MealDataRepositoryImpl;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class HomePresenter implements HomeContract.Presenter  {

    private HomeContract.View view;
    MealDataRepositoryImpl mealRepo;
    CategoryDataRepositoryImpl categoryRepo;

    public HomePresenter(HomeContract.View view, MealDataRepositoryImpl mealRepo, CategoryDataRepositoryImpl categoryRepo) {
        this.view = view;
        this.mealRepo = mealRepo;
        this.categoryRepo = categoryRepo;
    }


    @Override
    public void getMeals() {

    }

    @Override
    public Single<CategoryResponse>  getCategories() {
       return categoryRepo.getCategoriesRemote();
    }

    @Override
    public Single<MealsResponse> getAreas() {
        return mealRepo.getAreasRemote();
    }
}
