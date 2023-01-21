package com.restaurant.dinehouse.model;

import com.restaurant.dinehouse.util.SystemConstants;

public interface DailyAggregateItems{
    String getName();
    Integer getQuantity();
    Long getAmount();
    SystemConstants.OrderType getType();
    SystemConstants.OrderStatus getStatus();
}
