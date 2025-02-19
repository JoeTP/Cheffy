package com.example.cheffy.features.favorite.presenter;

import android.util.Log;

import com.example.cheffy.features.favorite.contract.FavoriteContract;
import com.example.cheffy.repository.MealDataRepositoryImpl;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritePresenter implements FavoriteContract.Presenter {
    private static final String TAG = "TEST";

    FavoriteContract.View view;
    MealDataRepositoryImpl repo;
    String userId;
    DatabaseReference dbRef;

    public FavoritePresenter(FavoriteContract.View view, MealDataRepositoryImpl repo, String userId) {
        this.repo = repo;
        this.view = view;
        this.userId = userId;


        dbRef = FirebaseDatabase.getInstance().getReference()
                .child("root")
                .child("users");

    }

    @Override
    public void getFavoriteMeals() {
        repo.getMealsFromFavorites(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MealsResponse.Meal>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onSuccess(@NonNull List<MealsResponse.Meal> meals) {
                        Log.i(TAG, "onSuccess: " + userId);
                        Log.i(TAG, "onSuccess GET MEALSE: " + meals.size());
                        view.showFavoriteMeals(meals);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });
    }

//    @Override
//    public void unfavorite(String idMeal) {
//        repo.removeMealFromFavorites(idMeal)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe();
//    }

    @Override
    public void removeFromFavorite(MealsResponse.Meal meal) {
        repo.removeFavoriteMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> removeFavoriteMealFromFireBase(meal),
                        throwable -> Log.e(TAG,
                        "removeFromFavorite: ", throwable));
    }

    void removeFavoriteMealFromFireBase(MealsResponse.Meal meal) {
        Log.i(TAG, "removePlanMealToFireBase: " + meal.getId());
        dbRef.child(meal.getId())
                .child("favorite")
                .child(meal.getIdMeal())
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "REMOVEDDDDD: ");
                            //TOAST
                            getFavoriteMeals();
                        } else {
                            Log.i(TAG, "FAILED TO REMOVEEE: ");
                            //TOAST
                        }
                    }
                });
    }
}
