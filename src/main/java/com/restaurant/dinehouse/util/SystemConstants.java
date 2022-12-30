package com.restaurant.dinehouse.util;

public class SystemConstants {

    public static final String BAD_REQUEST = "BAD_REQUEST";
    public static final String SUCCESS="SUCCESS";
    public static final Integer TOKEN_LIFE_TIME=360;
    public static final String USER_ACTIVE_STATUS="ACTIVE";

    public enum OrderStatus {
        ACTIVE,
        BLOCKED,
        PENDING,
        DECLINED,
        PREPARING,
        DELIVERED
    }

}
