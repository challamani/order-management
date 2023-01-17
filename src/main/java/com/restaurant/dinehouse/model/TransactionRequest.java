package com.restaurant.dinehouse.model;

import com.restaurant.dinehouse.util.SystemConstants;
import lombok.Data;

@Data
public class TransactionRequest {
    private String userId;
    private String orderId;
    private SystemConstants.PaymentMethod method;
    private SystemConstants.TranGroup type;
    private Double amount;
    private String description;
    private Long id;
}
