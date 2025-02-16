package com.example.cheffy.features.search.view;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cheffy.R;
import com.example.cheffy.features.search.contract.SearchContract;
import com.example.cheffy.features.search.presenter.SearchPresenter;
import com.example.cheffy.repository.database.meal.MealsLocalSourceImpl;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.network.meal.MealDataRepositoryImpl;
import com.example.cheffy.repository.network.meal.MealsRemoteSourceImpl;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchFragment extends Fragment implements OnMealCardClick, SearchContract.View {

    private static final String TAG = "TEST";
    SearchPresenter presenter;
    TextView tvTitle;
    EditText etSearch;
    RecyclerView recyclerView;
    SearchRecyclerAdapter adapter;
    List<MealsResponse.Meal> meals;
    private Disposable disposable;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public SearchFragment() {
    }

    void initUI(View view) {
        tvTitle = view.findViewById(R.id.tvTitle);
        etSearch = view.findViewById(R.id.etSearch);
        recyclerView = view.findViewById(R.id.recyclerView);
        meals = List.of(SearchFragmentArgs.fromBundle(getArguments()).getMeals());
        adapter = new SearchRecyclerAdapter(meals, getContext(), this);
        recyclerView.setAdapter(adapter);
    }

    String ss;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initUI(view);
        tvTitle.setText(SearchFragmentArgs.fromBundle(getArguments()).getFilter());
        presenter = new SearchPresenter(this, getMealRepositoryInstance(getContext()));

        Observable<String> observable = Observable.create(emitter -> {
            etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    emitter.onNext(s.toString().trim().toLowerCase());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        });

        disposable =
                observable.subscribeOn(Schedulers.io())
                        .debounce(500, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(s -> {
                            List<MealsResponse.Meal> filtered = meals.stream()
                                    .filter(meal -> meal.getStrMeal().toLowerCase().contains(s))
                                    .collect(Collectors.toList());
                            adapter.updateList(filtered);
                        });


        return view;
    }


    private MealDataRepositoryImpl getMealRepositoryInstance(Context context) {
        return MealDataRepositoryImpl.getInstance(MealsRemoteSourceImpl.getInstance(), MealsLocalSourceImpl.getInstance(context));
    }

    @Override
    public void onCardClick(MealsResponse.Meal meal) {
        Log.i(TAG, "onCardClick: " + meal.getStrMeal());
        Navigation.findNavController(requireView())
                .navigate(SearchFragmentDirections.actionSearchFragmentToMealFragment(meal));
    }

    @Override
    public void onFavoriteClick(MealsResponse.Meal meal) {
        Log.i(TAG, "onFavoriteClick: ");
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {

    }
}