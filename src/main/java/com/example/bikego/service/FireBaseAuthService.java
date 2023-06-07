package com.example.bikego.service;


import com.google.firebase.auth.FirebaseAuthException;


public interface FireBaseAuthService {
    String authenticateUser(String idToken) throws FirebaseAuthException;

    String refreshAccessToken(String refreshToken) throws FirebaseAuthException;

}
