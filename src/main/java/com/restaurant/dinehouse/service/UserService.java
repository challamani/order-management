package com.restaurant.dinehouse.service;

import com.restaurant.dinehouse.model.User;

import java.util.List;

public interface UserService {
    User addUser(User user);
    List<User> getUsers();
    User updateUser(User user);
}
