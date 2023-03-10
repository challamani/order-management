package com.restaurant.dinehouse.controller;

import com.restaurant.dinehouse.email.EmailDetails;
import com.restaurant.dinehouse.email.EmailService;
import com.restaurant.dinehouse.model.AuthRequest;
import com.restaurant.dinehouse.model.AuthResponse;
import com.restaurant.dinehouse.model.Response;
import com.restaurant.dinehouse.model.User;
import com.restaurant.dinehouse.service.UserService;
import com.restaurant.dinehouse.util.SystemConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EmailService emailService;

    @PostMapping("/user")
    public ResponseEntity<Response<User>> createUser( @RequestBody User user) {
        User modifiedUser = userService.addUser(user);
        modifiedUser.setPwd(null);
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS, modifiedUser));
    }

    @PutMapping("/user")
    public ResponseEntity<Response<User>> updateUser(@RequestBody User user) {
        User modifiedUser = userService.addUser(user);
        modifiedUser.setPwd(null);
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS, modifiedUser));
    }

    @GetMapping("/users")
    public ResponseEntity<Response<List<User>>> getUsers() {
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS, userService.getUsers()));
    }

    @PostMapping("/send-email")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseEntity<Response<String>> sendEmail(@RequestBody EmailDetails emailDetails) {
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS, emailService.sendSimpleMail(emailDetails)));
    }
}
