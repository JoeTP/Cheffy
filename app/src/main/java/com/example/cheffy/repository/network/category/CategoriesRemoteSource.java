package com.example.cheffy.repository.network.category;

import com.example.cheffy.repository.network.NetworkCallback;

public interface CategoriesRemoteSource {
    void getCategoriesFromAPI(NetworkCallback callback);
}
