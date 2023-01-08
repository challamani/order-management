package com.restaurant.dinehouse.controller;

import com.restaurant.dinehouse.model.Order;
import com.restaurant.dinehouse.model.Response;
import com.restaurant.dinehouse.model.Transaction;
import com.restaurant.dinehouse.service.TranService;
import com.restaurant.dinehouse.util.SystemConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TransactionController {

    private final TranService tranService;

    @PostMapping("/expenses")
    public ResponseEntity<Response<List<Transaction>>> saveExpenseRecords(@RequestBody List<Transaction> transactions) {
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS, tranService.saveTransactions(transactions)));
    }

    @PostMapping("/order-payment")
    public ResponseEntity<Response<Transaction>> payForOrder(@RequestBody Transaction transaction) {
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS, tranService.saveOrderPaymentInfo(transaction)));
    }
}
