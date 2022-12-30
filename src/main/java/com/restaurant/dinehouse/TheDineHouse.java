package com.restaurant.dinehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TheDineHouse {

    //start-up event to load system-users
    public static void main(String[] args) {
        SpringApplication.run(TheDineHouse.class, args);
    }
}
