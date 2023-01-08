package com.restaurant.dinehouse.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@Data
public class AuthResponse {
    private String token;
    private Integer lifeTime;
    private String userId;
    private Date expiresOn;
    private boolean isAdmin;
}
