package com.restaurant.dinehouse.controller;

import com.restaurant.dinehouse.model.*;
import com.restaurant.dinehouse.service.OrderService;
import com.restaurant.dinehouse.util.SystemConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @PostMapping("/order")
    public ResponseEntity<Response<Order>> createOrder(@RequestBody Order order) {
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS, orderService.addOrder(order)));
    }

    @PutMapping("/order")
    public ResponseEntity<Response<Order>> updateOrder(@RequestBody Order order) {
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS, orderService.updateOrder(order)));
    }

    @PostMapping("/location")
    public ResponseEntity<Response<Location>> createLocation(@RequestBody Location location) {
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS, orderService.addLocation(location)));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Response<Order>> getOrder(@PathVariable(name = "orderId") Long orderId) {
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS, orderService.getOrderById(orderId)));
    }

    @GetMapping("/order/{userId}")
    public ResponseEntity<Response<List<Order>>> getOrderByUser(@PathVariable(name = "userId") String userId) {
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS, orderService.getOrdersByUser(userId)));
    }


}
