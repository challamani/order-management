package com.restaurant.dinehouse.repository;

import com.restaurant.dinehouse.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order,Long> {
}
