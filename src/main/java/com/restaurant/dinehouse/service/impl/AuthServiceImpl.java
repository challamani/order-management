package com.restaurant.dinehouse.service.impl;


import com.restaurant.dinehouse.model.AgentSession;
import com.restaurant.dinehouse.model.AuthRequest;
import com.restaurant.dinehouse.model.AuthResponse;
import com.restaurant.dinehouse.repository.SessionRepository;
import com.restaurant.dinehouse.service.AuthService;
import com.restaurant.dinehouse.service.SystemConfig;
import com.restaurant.dinehouse.util.CipherUtil;
import com.restaurant.dinehouse.util.SystemConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final SystemConfig systemConfig;
    private final SessionRepository sessionRepository;
    public AuthResponse login(AuthRequest request) {
        try {
            String cipherText = systemConfig.getEncryptedPassword(request.getUserId());
            log.info("cipherText :: {}" + cipherText);
            String generatedText = CipherUtil.getSHA256Digest(request.getPwd());

            if (cipherText.equals(generatedText)) {
                AuthResponse authResponse = new AuthResponse();
                authResponse.setToken(UUID.randomUUID().toString());
                authResponse.setUserId(request.getUserId());
                authResponse.setLifeTime(SystemConstants.TOKEN_LIFE_TIME);

                AgentSession agentSession = new AgentSession();
                agentSession.setAgentId(request.getUserId());
                agentSession.setStatus(SystemConstants.SUCCESS);
                agentSession.setLiftTime(SystemConstants.TOKEN_LIFE_TIME);
                agentSession.setToken(authResponse.getToken());

                sessionRepository.save(agentSession);
                return authResponse;
            }
        } catch (Exception ex) {
            log.info("failed to get credentials {}" + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<AgentSession> getAll() {
        List<AgentSession> sessions = new ArrayList<>();
        sessionRepository.findAll().iterator().forEachRemaining(sessions::add);
        return sessions;
    }
}
