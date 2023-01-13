package com.restaurant.dinehouse.repository;

import com.restaurant.dinehouse.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order,Long> {

    List<Order> findByUserId(String userId);

    @Query(value = "select * from order_master where created_on >= CURDATE() and created_on < CURDATE() + INTERVAL 1 DAY", nativeQuery = true)
    List<Order> findCurrentDateOrders();
}
