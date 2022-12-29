package com.restaurant.dinehouse.service;

import com.restaurant.dinehouse.model.AgentSession;
import com.restaurant.dinehouse.model.AuthRequest;
import com.restaurant.dinehouse.model.AuthResponse;

import java.util.List;

public interface AuthService {
    AuthResponse login(AuthRequest request);

    List<AgentSession> getAll();
}
