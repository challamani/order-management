package com.restaurant.dinehouse.service.impl;

import com.restaurant.dinehouse.model.User;
import com.restaurant.dinehouse.repository.UserRepository;
import com.restaurant.dinehouse.service.UserService;
import com.restaurant.dinehouse.util.CipherUtil;
import com.restaurant.dinehouse.util.SystemConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User addUser(User user) {
        String encodedValue = CipherUtil.getSHA256Digest(user.getPwd());
        user.setPwd(encodedValue);
        user.setStatus(SystemConstants.USER_ACTIVE_STATUS);
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(user -> {
            user.setPwd(null);
            users.add(user);
        });
        return users;
    }

}
