package com.restaurant.dinehouse.controller;

import com.restaurant.dinehouse.model.*;
import com.restaurant.dinehouse.service.ItemService;
import com.restaurant.dinehouse.util.SystemConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/categories")
    public ResponseEntity<Response<List<Category>>> getCategories() {
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS, itemService.getCategories()));
    }

    @GetMapping("/items")
    public ResponseEntity<Response<List<Item>>> getItems() {
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS, itemService.getItems()));
    }

    @GetMapping("/baseInfo")
    public ResponseEntity<Response<BaseInfo>> getBaseInfo() {
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS, itemService.getBaseInfo()));
    }

}
