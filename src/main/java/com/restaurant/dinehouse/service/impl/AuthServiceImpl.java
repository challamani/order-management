package com.restaurant.dinehouse.service.impl;


import com.restaurant.dinehouse.model.User;
import com.restaurant.dinehouse.model.UserSession;
import com.restaurant.dinehouse.model.AuthRequest;
import com.restaurant.dinehouse.model.AuthResponse;
import com.restaurant.dinehouse.repository.SessionRepository;
import com.restaurant.dinehouse.repository.UserRepository;
import com.restaurant.dinehouse.service.AuthService;
import com.restaurant.dinehouse.service.SystemConfig;
import com.restaurant.dinehouse.util.CipherUtil;
import com.restaurant.dinehouse.util.SystemConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final SystemConfig systemConfig;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    public AuthResponse login(AuthRequest request) {
        try {
            User user = userRepository.findByUserId(request.getUserId());
            //check user status
            String cipherText = user.getPwd();
            log.info("cipherText from DB userId {} :: {}", request.getUserId(), cipherText);
            String generatedText = CipherUtil.getSHA256Digest(request.getPwd());

            if (cipherText.equals(generatedText)) {
                //Update & record if exists
                UserSession userSession = new UserSession();
                userSession.setUserId(request.getUserId());
                userSession.setLiftTime(SystemConstants.TOKEN_LIFE_TIME);
                userSession.setToken(UUID.randomUUID().toString());
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MINUTE, SystemConstants.TOKEN_LIFE_TIME);

                userSession.setExpireOn(calendar.getTime());
                sessionRepository.save(userSession);
                return createAuthResponse(userSession);
            }
        } catch (Exception ex) {
            log.info("failed to get credentials {}", ex.getMessage());
        }
        //throw runtime exception
        return null;
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
