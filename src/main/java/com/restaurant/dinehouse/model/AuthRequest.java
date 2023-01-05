package com.restaurant.dinehouse.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class AuthRequest {
    private String userId;
    private String password;
}
