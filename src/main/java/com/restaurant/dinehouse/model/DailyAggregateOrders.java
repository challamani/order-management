package com.restaurant.dinehouse.model;


import com.restaurant.dinehouse.util.SystemConstants;

public interface DailyAggregateOrders {
    Long getAmount();
    SystemConstants.OrderStatus getStatus();
    Integer getQuantity();
    String getUserId();
    String getServedBy();
    SystemConstants.OrderType getType();
    SystemConstants.PaymentMethod getPaymentMethod();
}
