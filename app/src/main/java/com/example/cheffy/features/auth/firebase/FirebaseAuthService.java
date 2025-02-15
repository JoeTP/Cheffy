package com.example.cheffy.features.auth.firebase;

public interface FirebaseAuthService {
    void registerUser(String email, String password);

    void loginUser(String email, String password);
}
