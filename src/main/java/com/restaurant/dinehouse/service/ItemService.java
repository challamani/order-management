package com.restaurant.dinehouse.service;

import com.restaurant.dinehouse.model.BaseInfo;
import com.restaurant.dinehouse.model.Category;
import com.restaurant.dinehouse.model.Item;

import java.util.List;

public interface ItemService {

    List<Category> getCategories();

    List<Item> getItems();

    List<Item> getItemsByCategory(Integer categoryId);

    Item getItemById(Long itemId);

    BaseInfo getBaseInfo();
}
