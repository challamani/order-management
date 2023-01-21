package com.restaurant.dinehouse.model;


import com.restaurant.dinehouse.util.SystemConstants;

public interface DailyAggregateTrans {

    SystemConstants.PaymentMethod getPaymentMethod();

    String getUserId();

    Long getAmount();

    SystemConstants.TranGroup getTranGroup();

    SystemConstants.TranType getType();
}
