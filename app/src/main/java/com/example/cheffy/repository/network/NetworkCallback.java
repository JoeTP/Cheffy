package com.example.cheffy.repository.network;

import com.example.cheffy.repository.models.category.CategoryResponse;

import java.util.List;

public interface NetworkCallback<T>{
    void onSuccess(T type);
    void onFailure(String message);
}
