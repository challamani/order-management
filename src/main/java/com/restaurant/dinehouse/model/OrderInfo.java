package com.restaurant.dinehouse.model;

import com.restaurant.dinehouse.util.SystemConstants;


public interface OrderInfo {
    Long getId();

    SystemConstants.OrderStatus getStatus();

    String getUserId();

    Double getPrice();

    Double getPayableAmount();

    String getDescription();

    SystemConstants.OrderType getType();

    String getAddress();

    String getServedBy();

    SystemConstants.PaymentMethod getPaymentMethod();
}
