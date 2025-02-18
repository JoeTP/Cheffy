package com.example.cheffy.utils;

import com.example.cheffy.features.auth.model.User;

public class Caching {
    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Caching.user = user;
    }
}
