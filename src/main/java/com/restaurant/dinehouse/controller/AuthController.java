package com.restaurant.dinehouse.controller;

import com.restaurant.dinehouse.model.UserSession;
import com.restaurant.dinehouse.model.AuthRequest;
import com.restaurant.dinehouse.model.AuthResponse;
import com.restaurant.dinehouse.model.Response;
import com.restaurant.dinehouse.service.AuthService;
import com.restaurant.dinehouse.util.SystemConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Response<AuthResponse>> authenticate(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authService.login(authRequest);
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS, authResponse));
    }

    @GetMapping("/sessions")
    public ResponseEntity<Response<List<UserSession>>> getSessions(){
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS,authService.getAll()));
    }
}
