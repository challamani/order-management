package com.restaurant.dinehouse.service.impl;

import com.restaurant.dinehouse.model.BaseInfo;
import com.restaurant.dinehouse.model.Category;
import com.restaurant.dinehouse.model.Item;
import com.restaurant.dinehouse.repository.CategoryRepository;
import com.restaurant.dinehouse.repository.ItemRepository;
import com.restaurant.dinehouse.repository.LocationRepository;
import com.restaurant.dinehouse.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;

    @Override
    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        Iterable<Category> categoryIterable = categoryRepository.findAll();
        categoryIterable.iterator().forEachRemaining(categories::add);
        return categories;
    }

    @Override
    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        Iterable<Item> itemIterable = itemRepository.findAll();
        itemIterable.iterator().forEachRemaining(items::add);
        return items;
    }

    @Override
    public List<Item> getItemsByCategory(Integer categoryId) {
        return null;
    }

    @Override
    public Item getItemById(Long itemId) {
        return itemRepository.findById(itemId).orElse(null);
    }

    @Override
    public BaseInfo getBaseInfo() {
        BaseInfo baseInfo = new BaseInfo();
        baseInfo.setCategories(new ArrayList<>());
        baseInfo.setLocations(new ArrayList<>());
        baseInfo.setItems(new ArrayList<>());

        categoryRepository.findAll().iterator().forEachRemaining(baseInfo.getCategories()::add);
        itemRepository.findAll().iterator().forEachRemaining(baseInfo.getItems()::add);
        locationRepository.findAll().iterator().forEachRemaining(baseInfo.getLocations()::add);
        return baseInfo;
    }
}
