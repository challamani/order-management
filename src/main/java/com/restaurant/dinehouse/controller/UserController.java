package com.restaurant.dinehouse.controller;

import com.restaurant.dinehouse.model.AuthRequest;
import com.restaurant.dinehouse.model.AuthResponse;
import com.restaurant.dinehouse.model.Response;
import com.restaurant.dinehouse.model.User;
import com.restaurant.dinehouse.service.UserService;
import com.restaurant.dinehouse.util.SystemConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<Response<User>> createUser(@RequestBody User user) {
        User modifiedUser = userService.addUser(user);
        modifiedUser.setPwd(null);
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS, modifiedUser));
    }

    @GetMapping("/users")
    public ResponseEntity<Response<List<User>>> getUsers() {
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS, userService.getUsers()));
    }
}
