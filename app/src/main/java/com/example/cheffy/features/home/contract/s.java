//// HomeContract.java
//package com.example.cheffy.features.home.contract;
//
//import android.content.Context;
//import android.util.Log;
//
//import com.example.cheffy.repository.models.category.CategoryResponse;
//import com.example.cheffy.repository.models.meal.MealsResponse;
//import io.reactivex.rxjava3.core.Single;
//
//public interface HomeContract {
//    interface View {
//        void showLoading();
//        void hideLoading();
//        void showError(String message);
//        void updateUserName(String name);
//        void updateCategories(CategoryResponse categories);
//        void updateAreas(MealsResponse areas);
//        void clearList();
//        Context getViewContext();
//    }
//
//    interface Presenter {
//        void onViewCreated();
//        void onDestroy();
//        void loadUserData();
//        void handleCategorySelection();
//        void handleCountrySelection();
//        void handleIngredientSelection();
//        void onCardClicked(Object item);
//    }
//}
//
//// HomePresenter.java
//package com.example.cheffy.features.home.presenter;
//
//import android.util.Log;
//import com.example.cheffy.features.home.contract.HomeContract;
//import com.example.cheffy.repository.models.category.CategoryResponse;
//import com.example.cheffy.repository.models.meal.MealsResponse;
//import com.example.cheffy.repository.network.category.CategoryDataRepositoryImpl;
//import com.example.cheffy.repository.network.meal.MealDataRepositoryImpl;
//import com.example.cheffy.utils.AppStrings;
//import com.example.cheffy.utils.SharedPreferencesHelper;
//import com.google.firebase.firestore.FirebaseFirestore;
//import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
//import io.reactivex.rxjava3.core.Single;
//import io.reactivex.rxjava3.disposables.CompositeDisposable;
//import io.reactivex.rxjava3.schedulers.Schedulers;
//
//public class HomePresenter implements HomeContract.Presenter {
//    private static final String TAG = "HomePresenter";
//    private final HomeContract.View view;
//    private final MealDataRepositoryImpl mealRepo;
//    private final CategoryDataRepositoryImpl categoryRepo;
//    private final SharedPreferencesHelper sharedPreferences;
//    private final FirebaseFirestore firestore;
//    private final CompositeDisposable disposables;
//
//    public HomePresenter(HomeContract.View view, MealDataRepositoryImpl mealRepo,
//                         CategoryDataRepositoryImpl categoryRepo) {
//        this.view = view;
//        this.mealRepo = mealRepo;
//        this.categoryRepo = categoryRepo;
//        this.sharedPreferences = new SharedPreferencesHelper(view.getViewContext());
//        this.firestore = FirebaseFirestore.getInstance();
//        this.disposables = new CompositeDisposable();
//    }
//
//    @Override
//    public void onViewCreated() {
//        loadUserData();
//    }
//
//    @Override
//    public void loadUserData() {
//        view.showLoading();
//        disposables.add(
//                sharedPreferences.getString(AppStrings.CURRENT_USERID, "Guest")
//                        .flatMap(userId ->
//                                Single.create(emitter ->
//                                        firestore.collection(AppStrings.USER_COLLECTION)
//                                                .document(userId)
//                                                .get()
//                                                .addOnSuccessListener(document -> {
//                                                    if (document != null && document.exists()) {
//                                                        String name = document.getString("name");
//                                                        emitter.onSuccess(name != null ? name : "Guest");
//                                                    } else {
//                                                        emitter.onSuccess("Guest");
//                                                    }
//                                                })
//                                                .addOnFailureListener(emitter::onError)
//                                )
//                        )
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                userName -> {
//                                    view.hideLoading();
//                                    view.displayUsername( userName);
//                                },
//                                throwable -> {
//                                    Log.e(TAG, "Error loading user data", throwable);
//                                    view.hideLoading();
//                                    view.showError("Failed to load user data");
//                                    view.displayUsername("Guest");
//                                }
//                        )
//        );
//    }
//
//    @Override
//    public void handleCategorySelection() {
//        view.showLoading();
//                categoryRepo.getCategoriesRemote()
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                categories -> {
//                                    view.hideLoading();
//                                    view.updateCategories(categories);
//                                },
//                                throwable -> {
//                                    view.hideLoading();
//                                    view.showError("Failed to load categories");
//                                    Log.e(TAG, "Error loading categories", throwable);
//                                }
//        );
//    }
//
//    @Override
//    public void handleCountrySelection() {
//        view.showLoading();
//        disposables.add(
//                mealRepo.getAreasRemote()
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                areas -> {
//                                    view.hideLoading();
//                                    view.updateAreas(areas);
//                                },
//                                throwable -> {
//                                    view.hideLoading();
//                                    view.showError("Failed to load countries");
//                                    Log.e(TAG, "Error loading countries", throwable);
//                                }
//                        )
//        );
//    }
//
//
//    @Override
//    public void onCardClicked(Object item) {
//        if (item instanceof CategoryResponse.Category) {
//            // Handle category click
//            CategoryResponse.Category category = (CategoryResponse.Category) item;
//            // Implement navigation or other actions
//        } else if (item instanceof MealsResponse.Meal) {
//            // Handle meal click
//            MealsResponse.Meal meal = (MealsResponse.Meal) item;
//            // Implement navigation or other actions
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        disposables.clear();
//    }
//}
//
//// HomeFragment.java
//package com.example.cheffy.features.home.view;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.RecyclerView;
//import com.example.cheffy.R;
//import com.example.cheffy.features.home.contract.HomeContract;
//import com.example.cheffy.repository.models.category.CategoryResponse;
//import com.example.cheffy.repository.models.meal.MealsResponse;
//import com.google.android.material.chip.ChipGroup;
//import java.util.ArrayList;
//
//public class HomeFragment extends Fragment implements HomeContract.View, OnCardClick {
//    private HomeContract.Presenter presenter;
//    private RecyclerView recyclerView;
//    private TextView tvGreetingMsg;
//    private TextView tvUserName;
//    private TextView tvTodaySpecial;
//    private ChipGroup chipGroup;
//    private HomeRecyclerAdapter adapter;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        initUI(view);
//        setupPresenter();
//        setupChipGroupListener();
//        return view;
//    }
//
//    private void setupPresenter() {
//        presenter = new HomePresenter(
//                this,
//                getMealRepositoryInstance(getContext()),
//                getCategoryRepositoryImpl()
//        );
//        presenter.onViewCreated();
//    }
//
//    private void setupChipGroupListener() {
//        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
//
//            if (checkedIds.contains(R.id.categoryChip)) {
//                presenter.handleCategorySelection();
//            } else if (checkedIds.contains(R.id.countryChip)) {
//                presenter.handleCountrySelection();
//            } else if (checkedIds.contains(R.id.ingredientChip)) {
//                presenter.handleIngredientSelection();
//            }
//        });
//    }
//
//    public void displayUsername(String name) {
//        tvUserName.setText(name);
//    }
//
//    @Override
//    public void updateCategories(CategoryResponse categories) {
//        if (adapter == null) {
//            adapter = new HomeRecyclerAdapter(categories.getCategories(),
//                    getContext(), this);
//            recyclerView.setAdapter(adapter);
//        } else {
//            adapter.updateList(categories.getCategories());
//        }
//    }
//
//    @Override
//    public void updateAreas(MealsResponse areas) {
//        if (adapter == null) {
//            adapter = new HomeRecyclerAdapter(areas.getMeals(),
//                    getContext(), this);
//            recyclerView.setAdapter(adapter);
//        } else {
//            adapter.updateList(areas.getMeals());
//        }
//    }
//
//    @Override
//    public void clearList() {
//        if (adapter != null) {
//            adapter.updateList(new ArrayList<>());
//        }
//    }
//
//    @Override
//    public void onCardClick(Object item) {
//        presenter.onCardClicked(item);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        presenter.onDestroy();
//    }
//
//    // Implementation of other required methods...
//}