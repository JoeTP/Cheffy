package com.example.cheffy.repository.network.category;

import com.example.cheffy.repository.models.category.CategoryResponse;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface CategoryDataRepository {

    Single<CategoryResponse> getCategoriesRemote();

}
