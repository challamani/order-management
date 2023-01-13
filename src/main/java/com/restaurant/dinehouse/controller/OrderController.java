package com.restaurant.dinehouse.controller;

import com.restaurant.dinehouse.model.*;
import com.restaurant.dinehouse.service.OrderService;
import com.restaurant.dinehouse.util.SystemConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Response<Order>> updateOrder( @RequestBody Order order) {
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

    @GetMapping("/orders/{userId}")
    public ResponseEntity<Response<List<Order>>> getOrdersByUser( @PathVariable(name = "userId") String userId) {
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS, orderService.getOrdersByUser(userId)));
    }

    @GetMapping("/orders")
    public ResponseEntity<Response<List<Order>>> getAllCurrentDateOrders() {
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS, orderService.getCurrentDateOrders(true)));
    }

    @GetMapping("/view/orders")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseEntity<List<Order>> getTodayOrders() {
        return ResponseEntity.ok(orderService.getCurrentDateOrders(false));
    }

    @GetMapping("/bill/{orderId}")
    public ResponseEntity<Response<Boolean>> generateBill( @PathVariable(name = "orderId") Long orderId) {

        if (orderService.generateBill(orderId)) {
            return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS, true));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Response<>(SystemConstants.SUCCESS, false));

    }

}
