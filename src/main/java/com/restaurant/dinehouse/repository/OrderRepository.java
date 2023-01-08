package com.restaurant.dinehouse.repository;

import com.restaurant.dinehouse.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order,Long> {

    List<Order> findByUserId(String userId);
}
