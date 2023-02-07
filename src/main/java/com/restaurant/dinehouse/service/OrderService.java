package com.restaurant.dinehouse.service;

import com.restaurant.dinehouse.model.*;

import java.util.List;

public interface OrderService {

    Location addLocation(Location location);

    Location updateLocation(Location location);

    Order addOrder(Order order);

    Order updateOrder(Order order);

    List<Order> getOrdersByUser(String userId);

    Order getOrderById(Long orderId);

    Order deleteOrderById(Long orderId);

    Boolean generateBill(Long orderId);

    List<Order> getCurrentDateOrders(boolean includeItems);

    List<OrderInfo> getCurrentDateOrderInfo();

    List<DailyAggregateItems> getDailyReportOnItems();

    List<DailyAggregateOrders> getDailyReportOnOrders();
}
