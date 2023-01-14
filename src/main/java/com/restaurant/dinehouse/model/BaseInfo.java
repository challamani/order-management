package com.restaurant.dinehouse.model;

import com.restaurant.dinehouse.util.SystemConstants;
import lombok.Data;

import java.util.List;

@Data
public class BaseInfo {

    private List<Location> locations;
    private List<Category> categories;
    private List<Item> items;
    private List<SystemConstants.PaymentMethod> paymentMethods;
    private List<SystemConstants.TranGroup> tranGroups;
    private List<String> servers;

}
