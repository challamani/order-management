package com.restaurant.dinehouse.model;

import com.restaurant.dinehouse.util.SystemConstants;
import lombok.Data;

@Data
public class BalanceSheetResponse {

    private String activity;
    private SystemConstants.PaymentMethod paymentMethod;
    private SystemConstants.TranType type;
    private String expected;
    private String actual;

}
