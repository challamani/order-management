package com.restaurant.dinehouse.service.impl;

import com.restaurant.dinehouse.model.Category;
import com.restaurant.dinehouse.model.Item;
import com.restaurant.dinehouse.repository.CategoryRepository;
import com.restaurant.dinehouse.repository.ItemRepository;
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
    public Item getItemById(Integer itemId) {
        return null;
    }
}
