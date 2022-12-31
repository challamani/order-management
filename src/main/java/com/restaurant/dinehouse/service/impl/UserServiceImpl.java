package com.restaurant.dinehouse.service.impl;

import com.restaurant.dinehouse.model.User;
import com.restaurant.dinehouse.repository.UserRepository;
import com.restaurant.dinehouse.service.UserService;
import com.restaurant.dinehouse.util.CipherUtil;
import com.restaurant.dinehouse.util.SystemConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public User updateUser(User user) {
        User dbUser = userRepository.findByUserId(user.getUserId());
        if (Objects.isNull(dbUser)) {
            throw new RuntimeException("user not-found ::" + user.getUserId());
        }

        if (StringUtils.isNoneBlank(user.getPwd())) {
            dbUser.setPwd(CipherUtil.getSHA256Digest(user.getPwd()));
        }
        dbUser.setPhoneNo(ObjectUtils.defaultIfNull(user.getPhoneNo(), dbUser.getPhoneNo()));
        dbUser.setStatus(ObjectUtils.defaultIfNull(user.getStatus(), dbUser.getStatus()));
        dbUser.setAddress(ObjectUtils.defaultIfNull(user.getAddress(), dbUser.getAddress()));
        dbUser.setEmail(ObjectUtils.defaultIfNull(user.getEmail(), dbUser.getEmail()));
        return userRepository.save(dbUser);
    }

}
