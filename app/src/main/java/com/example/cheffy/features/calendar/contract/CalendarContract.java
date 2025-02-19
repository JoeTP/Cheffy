package com.example.cheffy.features.calendar.contract;

import com.example.cheffy.repository.models.plan.PlanModel;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface CalendarContract {
    interface View {
        void showMeals(List<PlanModel> meals);
        void showError(String message);
        void showLoading();
        void hideLoading();
    }
    interface Presenter {
        void getPlanMeals(String userId);
        void filterMealsByDate(String selectedDate);
        void deletePlan(PlanModel plan);
    }
}
