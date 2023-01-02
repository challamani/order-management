package com.restaurant.dinehouse.util;

public class SystemConstants {

    public static final String BAD_REQUEST = "BAD_REQUEST";
    public static final String SUCCESS="SUCCESS";
    public static final Integer TOKEN_LIFE_TIME=360;
    public static final String USER_ACTIVE_STATUS="ACTIVE";
    public static final String SYS_ADMIN_USER="admin";
    public static final String SYS_MISC_USER="misc";
    public static final String ITEM_ACTIVE_STATUS="ACTIVE";
    public enum OrderStatus {
        OPEN,
        CANCELLED,
        PENDING,
        DECLINED,
        PREPARING,
        DELIVERED
    }

    public enum OrderType {
        DINEIN,
        TAKEAWAY,
        ZOMATO,
        SWIGGY,
        DELIVERY,
        OTHER
    }
}
