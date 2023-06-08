package com.example.bikego.controller;

import com.example.bikego.dto.LoginForm;
import com.example.bikego.dto.ResponseObject;
import com.example.bikego.dto.SignInForm;
import com.example.bikego.dto.TokenRequestForm;
import com.example.bikego.exception.AuthenticationFailedException;
import com.example.bikego.exception.RegistrationException;
import com.example.bikego.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "for user login")
    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@RequestBody @Valid LoginForm loginForm) throws FirebaseAuthException {
        return userService.loginUser(loginForm);
    }
    @Operation(summary = "for user register")
    @PostMapping("/register")
    public ResponseEntity<ResponseObject> register(@RequestBody @Valid SignInForm signInForm) throws RegistrationException {
        return userService.registerUser(signInForm);
    }
    @Operation(summary = "for verify token from client")
    @PostMapping("/verify-token")
    public ResponseEntity<ResponseObject> verifyAccessToken(@RequestParam("idToken") String idToken) {
        return userService.verifyAccessToken(idToken);
    }
    @Operation(summary = "for get refresh token ")
    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseObject> refreshAccessToken(@RequestBody TokenRequestForm tokenRequestForm) {
        return userService.refreshAccessToken(tokenRequestForm);
    }
    @PostMapping("/logout")
    public ResponseEntity<ResponseObject> logout(HttpSession httpSession) {
        return userService.logout(httpSession);
    }

}
