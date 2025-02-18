package com.example.cheffy.features.calendar.presenter;

import com.example.cheffy.features.calendar.contract.CalendarContract;
import com.example.cheffy.repository.MealDataRepositoryImpl;
import com.example.cheffy.repository.models.plan.PlanModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CalendarPresenter implements CalendarContract.Presenter {

    private static final String TAG = "TEST";
    CalendarContract.View view;
    MealDataRepositoryImpl repo;

    public CalendarPresenter(CalendarContract.View view, MealDataRepositoryImpl repo) {
        this.view = view;
        this.repo = repo;
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
                        view.showMeals(planModels);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    @Override
    public void deletePlan(PlanModel plan) {
    }
}
