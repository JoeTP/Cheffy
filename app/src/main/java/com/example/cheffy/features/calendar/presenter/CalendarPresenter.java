package com.example.cheffy.features.calendar.presenter;

import android.util.Log;

import com.example.cheffy.features.calendar.contract.CalendarContract;
import com.example.cheffy.repository.MealDataRepositoryImpl;
import com.example.cheffy.repository.models.plan.PlanModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CalendarPresenter implements CalendarContract.Presenter {

    private static final String TAG = "TEST";
    CalendarContract.View view;
    MealDataRepositoryImpl repo;
    List<PlanModel> allMeals;
    DatabaseReference dbRef;

    public CalendarPresenter(CalendarContract.View view, MealDataRepositoryImpl repo) {
        this.view = view;
        this.repo = repo;
        dbRef = FirebaseDatabase.getInstance().getReference()
                .child("root")
                .child("users");
    }

    @Override
    public void getPlanMeals(String userId) {
        repo.getPlanMeals(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<PlanModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<PlanModel> planModels) {
                        allMeals = planModels;
                        List<PlanModel> filteredMeals = new ArrayList<>();
                        for (PlanModel meal : allMeals) {
                            if (meal.getDate().equals(getFormattedDate())) {
                                filteredMeals.add(meal);
                            }
                        }
                        view.showMeals(filteredMeals);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    @Override
    public void filterMealsByDate(String selectedDate) {
        List<PlanModel> filteredMeals = new ArrayList<>();
        for (PlanModel meal : allMeals) {
            if (meal.getDate().equals(selectedDate)) {
                filteredMeals.add(meal);
            }
        }
        view.showMeals(filteredMeals);
    }

    String getFormattedDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "-" + month + "-" + day;
    }

    @Override
    public void deletePlan(PlanModel plan) {
        repo.deletePlan(plan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    removePlanMealToFireBase(plan);
                   allMeals.remove(plan);
                   view.showMeals(allMeals);
                }, throwable -> Log.e(TAG, "deletePlan: ", throwable));

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
}
