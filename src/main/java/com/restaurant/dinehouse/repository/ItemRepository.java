package com.restaurant.dinehouse.repository;

import com.restaurant.dinehouse.model.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {
}
