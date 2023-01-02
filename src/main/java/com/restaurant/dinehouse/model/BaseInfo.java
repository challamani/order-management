package com.restaurant.dinehouse.model;

import lombok.Data;

import java.util.List;

@Data
public class BaseInfo {

    private List<Location> locations;
    private List<Category> categories;
    private List<Item> items;

}
