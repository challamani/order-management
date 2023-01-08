package com.restaurant.dinehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TheDineHouse {

    /***
     * TODO
     * 1. Transaction table for order payments & expenses
     * 2. Inventory table
     * 3. Order payment receipt printing
     * 4. Create a Token based user object with request scope
     * 5. Get all un-paid orders on that day
     * */
    //start-up event to load system-users
    public static void main(String[] args) {
        SpringApplication.run(TheDineHouse.class, args);
    }
}
