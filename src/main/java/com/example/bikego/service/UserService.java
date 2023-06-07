package com.example.bikego.service;

import com.example.bikego.dto.LoginForm;
import com.example.bikego.dto.ResponseObject;
import com.example.bikego.dto.SignInForm;
import com.example.bikego.dto.TokenRequestForm;
import com.example.bikego.entity.User;
import com.example.bikego.exception.AuthenticationFailedException;
import com.example.bikego.exception.RegistrationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.auth.FirebaseAuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ResponseObject> registerUser(SignInForm signInForm) throws RegistrationException;
    ResponseEntity<ResponseObject> loginUser(LoginForm loginForm) throws FirebaseAuthException;

    ResponseEntity<ResponseObject> verifyAccessToken(String token);
    ResponseEntity<ResponseObject> refreshAccessToken(TokenRequestForm tokenRequestForm);

    ResponseEntity<ResponseObject> logout(HttpSession httpSession);
    User getCurrentUser(String uid) throws AuthenticationFailedException;

    User findUserByEmail(String email);
    ResponseEntity<ResponseObject> getAllUser();

    ResponseEntity<ResponseObject> getUserDetail(String uid);
}
