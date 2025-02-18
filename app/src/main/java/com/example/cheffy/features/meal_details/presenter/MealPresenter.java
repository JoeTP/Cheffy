package com.example.cheffy.features.meal_details.presenter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;

import com.example.cheffy.features.meal_details.contract.MealContract;
import com.example.cheffy.repository.MealDataRepository;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.models.plan.PlanModel;
import com.example.cheffy.utils.Caching;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealPresenter implements MealContract.Presenter {

    private static final String TAG = "TEST";
    MealDataRepository repo;
    MealContract.View view;
    DatabaseReference dbRef;

    public MealPresenter(MealContract.View view, MealDataRepository repo, Context context) {
        this.repo = repo;
        this.view = view;
        dbRef = FirebaseDatabase.getInstance().getReference()
                .child("root")
                .child("users");

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
                        addFavoriteMealToFireBase(meal);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("TEST", "onError: " + e.getMessage());
                    }
                });

    }


    void addFavoriteMealToFireBase(MealsResponse.Meal meal) {
        dbRef.child(meal.getId())
                .child("favorite")
                .child(meal.getIdMeal())
                .setValue(meal)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //TOAST
                        } else {
                            //TOAST
                        }
                    }
                });
    }


    @Override
    public void isFavorite(String id, String idMeal) {
        repo.isInFavourite(id, idMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onSuccess(@NonNull Boolean aBoolean) {
                        view.ivFavorite(aBoolean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void insertToPlan(PlanModel plan) {
        plan.setId(Caching.getUser().getId());
        showDatePicker(plan);
    }
    private void showDatePicker(PlanModel plan) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(view.returnContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    Log.d(TAG, "Selected Date: " + selectedDate);
                    plan.setDate(selectedDate);
                    repo.insertPlan(plan)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    () -> {
                                        addPlanMealToFireBase(plan);
                                    },
                                    throwable ->
                                            Log.e(TAG, "insertToPlan: ", throwable)
                            );
                },
                year, month, day);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }


    @Override
    public void removePlanMeal(PlanModel meal) {
        removePlanMealToFireBase(meal);
    }

    void addPlanMealToFireBase(PlanModel meal) {
        dbRef.child(meal.getId())
                .child("plan")
                .child(meal.getDate()+"_" + meal.getMeal().getIdMeal())
                .setValue(meal)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //TOAST
                        } else {
                            //TOAST
                        }
                    }
                });
    }

    void removePlanMealToFireBase(PlanModel meal) {
        Log.i(TAG, "removePlanMealToFireBase: " + meal.getId());
        dbRef.child(meal.getId())
                .child("plan")
                .child(meal.getDate()  +"_"+ meal.getMeal().getIdMeal())
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "REMOVEDDDDD: ");
                            //TOAST
                        } else {
                            Log.i(TAG, "FAILED TO REMOVEEE: ");
                            //TOAST
                        }
                    }
                });
    }


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

                        } else {
                            Log.i(TAG, "FAILED TO REMOVEEE: ");
                            //TOAST
                        }
                    }
                });
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
