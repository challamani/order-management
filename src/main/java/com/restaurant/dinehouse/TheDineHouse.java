package com.restaurant.dinehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TheDineHouse {

    /***
     * TODO
     * 1. Transaction table for order payments & expenses
     * 2. Inventory table
     * 3. Order payment receipt printing */
    //start-up event to load system-users
    public static void main(String[] args) {
        SpringApplication.run(TheDineHouse.class, args);
    }
}
