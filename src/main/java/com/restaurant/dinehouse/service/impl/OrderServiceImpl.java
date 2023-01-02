package com.restaurant.dinehouse.service.impl;

import com.restaurant.dinehouse.exception.BadRequestException;
import com.restaurant.dinehouse.model.Location;
import com.restaurant.dinehouse.model.Order;
import com.restaurant.dinehouse.repository.ItemRepository;
import com.restaurant.dinehouse.repository.LocationRepository;
import com.restaurant.dinehouse.repository.OrderItemRepository;
import com.restaurant.dinehouse.repository.OrderRepository;
import com.restaurant.dinehouse.service.ItemService;
import com.restaurant.dinehouse.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final LocationRepository locationRepository;
    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final OrderItemRepository orderItemRepository;

    @Override
    public Location addLocation(Location location) {

        if(Objects.nonNull(location)){
            return locationRepository.save(location);
        }
        return null;
    }

    @Override
    public Location updateLocation(Location location) {

        if (Objects.nonNull(location) && Objects.nonNull(locationRepository.findById(location.getId()))) {
            return locationRepository.save(location);
        }
        throw new BadRequestException("invalid location found for update");
    }

    @Override
    @Transactional
    public Order addOrder(Order order) {
        if (Objects.nonNull(order) && !CollectionUtils.isEmpty(order.getOrderItems())) {
            Order dbOrder = orderRepository.save(order);
            Long orderId = dbOrder.getId();

            order.getOrderItems().stream().forEach(orderItem ->
            {
                orderItem.setItemName(itemService.getItemById(orderItem.getItemId()).getName());
                orderItem.setOrderId(orderId);
            });
            orderItemRepository.saveAll(order.getOrderItems());

            Optional<Order> dbOrderOptional = orderRepository.findById(orderId);
            if (dbOrderOptional.isPresent()) {
                dbOrder = dbOrderOptional.get();
                dbOrder.setOrderItems(orderItemRepository.findByOrderId(dbOrder.getId()));
                return dbOrder;
            }
        }
        throw new RuntimeException("failed to create given order!");
    }

    @Override
    @Transactional
    public Order updateOrder(Order order) {
        return null;
    }

    @Override
    public List<Order> getOrdersByUser(String userId) {
        return null;
    }

    @Override
    public Order getOrderById(Long orderId) {
        return fetchOrderById(orderId);
    }

    private Order fetchOrderById(Long orderId){
        Optional<Order> dbOrderOptional = orderRepository.findById(orderId);
        if (dbOrderOptional.isPresent()) {
            Order  dbOrder = dbOrderOptional.get();
            dbOrder.setOrderItems(orderItemRepository.findByOrderId(dbOrder.getId()));
            return dbOrder;
        }

        return null;
    }
}
