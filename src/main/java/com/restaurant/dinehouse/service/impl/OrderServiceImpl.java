package com.restaurant.dinehouse.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.restaurant.dinehouse.exception.BadRequestException;
import com.restaurant.dinehouse.model.Item;
import com.restaurant.dinehouse.model.Location;
import com.restaurant.dinehouse.model.Order;
import com.restaurant.dinehouse.print.PrinterService;
import com.restaurant.dinehouse.repository.LocationRepository;
import com.restaurant.dinehouse.repository.OrderItemRepository;
import com.restaurant.dinehouse.repository.OrderRepository;
import com.restaurant.dinehouse.service.ItemService;
import com.restaurant.dinehouse.service.OrderService;
import com.restaurant.dinehouse.util.SystemConstants;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final LocationRepository locationRepository;
    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final OrderItemRepository orderItemRepository;
    private final PrinterService printerService;

    @Override
    public Location addLocation(Location location) {
        if(Objects.nonNull(location)){
            return locationRepository.save(location);
        }
        return null;
    }

    @Override
    public Location updateLocation(Location location) {
        if (Objects.nonNull(location)
                && Objects.nonNull(locationRepository.findById(location.getId()))) {
            return locationRepository.save(location);
        }
        throw new BadRequestException("invalid location found for update");
    }

    @Override
    @Transactional
    public Order addOrder(Order order) {
        try {
            log.info("order {}", Json.mapper().writeValueAsString(order));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (Objects.nonNull(order) && !CollectionUtils.isEmpty(order.getOrderItems())) {
            return processAndReturnOrder(order);
        }
        throw new RuntimeException("failed to create given order!");
    }

    @Override
    @Transactional
    public Order updateOrder(Order order) {
        if (order.getId() > 0 && !CollectionUtils.isEmpty(order.getOrderItems())) {
            orderItemRepository.deleteByOrderId(order.getId());
            return processAndReturnOrder(order);
        }
        throw new RuntimeException("failed to update the given order!" + order.getId());
    }

    private Order processAndReturnOrder(Order order){
        AtomicReference<Double> orderAmount = new AtomicReference<>(0.0);
        order.getOrderItems().stream().forEach(orderItem -> {
            Item item = itemService.getItemById(orderItem.getItemId());
            orderItem.setItemName(itemService.getItemById(orderItem.getItemId()).getName());
            orderItem.setPrice(item.getPrice());
            orderAmount.updateAndGet(value -> (value + (item.getPrice() * orderItem.getQuantity())));
        });

        order.setPrice(orderAmount.get());
        order.setPayableAmount(orderAmount.get());
        Order dbOrder = orderRepository.save(order);
        Long orderId = dbOrder.getId();
        order.getOrderItems().stream().forEach(orderItem -> orderItem.setOrderId(orderId));
        orderItemRepository.saveAll(order.getOrderItems());
        return fetchOrderById(orderId);
    }
    @Override
    public List<Order> getOrdersByUser(String userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        orders.stream().forEach(order -> {
            order.setOrderItems(orderItemRepository.findByOrderId(order.getId()));
        });
        return orders;
    }

    @Override
    public Order getOrderById(Long orderId) {
        return fetchOrderById(orderId);
    }

    @Override
    public Boolean generateBill(Long orderId) {
        boolean isBillGenerated = printerService.print(orderId);
        if (isBillGenerated) {
            Order dbOrder = orderRepository.findById(orderId).get();
            dbOrder.setStatus(SystemConstants.OrderStatus.BILL_GENERATED);
            orderRepository.save(dbOrder);
        }
        return isBillGenerated;
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
