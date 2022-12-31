package com.restaurant.dinehouse.service.impl;


import com.restaurant.dinehouse.exception.BadRequestException;
import com.restaurant.dinehouse.exception.NotFoundException;
import com.restaurant.dinehouse.model.User;
import com.restaurant.dinehouse.model.UserSession;
import com.restaurant.dinehouse.model.AuthRequest;
import com.restaurant.dinehouse.model.AuthResponse;
import com.restaurant.dinehouse.repository.SessionRepository;
import com.restaurant.dinehouse.repository.UserRepository;
import com.restaurant.dinehouse.service.AuthService;
import com.restaurant.dinehouse.util.CipherUtil;
import com.restaurant.dinehouse.util.SystemConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    @Transactional
    public AuthResponse login(AuthRequest request) {

        User user = userRepository.findByUserId(request.getUserId());
        if (Objects.isNull(user) || !StringUtils.equals(SystemConstants.USER_ACTIVE_STATUS, user.getStatus())) {
            throw new NotFoundException("user not-found");
        }

        String cipherText = user.getPwd();
        log.info("cipherText from DB userId {} :: {}", request.getUserId(), cipherText);
        String generatedText = CipherUtil.getSHA256Digest(request.getPwd());

        if (cipherText.equals(generatedText)) {
            sessionRepository.deleteByUserId(request.getUserId());
            UserSession userSession = new UserSession();
            userSession.setUserId(request.getUserId());
            userSession.setLiftTime(SystemConstants.TOKEN_LIFE_TIME);
            userSession.setToken(UUID.randomUUID().toString());

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, SystemConstants.TOKEN_LIFE_TIME);
            userSession.setExpireOn(calendar.getTime());

            sessionRepository.save(userSession);
            return createAuthResponse(userSession);
        } else {
            throw new BadRequestException("Wrong credentials try again!");
        }
    }

    private AuthResponse createAuthResponse(UserSession userSession){
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(userSession.getToken());
        authResponse.setUserId(userSession.getUserId());
        authResponse.setLifeTime(SystemConstants.TOKEN_LIFE_TIME);
        authResponse.setExpiresOn(userSession.getExpireOn());
        return authResponse;
    }

    @Override
    public List<UserSession> getAll() {
        List<UserSession> sessions = new ArrayList<>();
        sessionRepository.findAll().iterator().forEachRemaining(sessions::add);
        return sessions;
    }
}
