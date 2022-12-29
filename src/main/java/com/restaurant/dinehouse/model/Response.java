package com.restaurant.dinehouse.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Response<T>{
    private String status;
    private T data;

    public Response(String status, T data) {
        this.status = status;
        this.data = data;
    }
}
