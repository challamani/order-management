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
        DELIVERED,
        BILL_GENERATED,
        PAID
    }

    public enum PaymentMethod {
        PhonePe,
        ZomatoPay,
        Cash,
        Card,
        Paytm,
        Pending
    }

    public enum TranGroup {
        Wages,
        Rent,
        Chicken,
        Gas,
        Vegetables,
        Grocery,
        Rice,
        Maintenance,
        AdvancePay,
        Other,

        Order
    }

    public enum OrderType {
        DINEIN,
        TAKEAWAY,
        ZOMATO,
        SWIGGY,
        DELIVERY,
        OTHER
    }

    public enum TranType {
        Cr,
        Dr
    }

    public class Store {
        public static final String name="The Dine House";
        public static final String address1="Plot No 366, Opp KIMS Hospital";
        public static final String address2="Srikakulam RD, 532005, AP";
        public static final String GSTNo="GST IN: 37CDHPC9120M1ZD";
        public static final String contactNo="+91 8074408548";
    }
}
