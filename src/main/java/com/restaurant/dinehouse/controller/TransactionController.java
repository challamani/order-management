package com.restaurant.dinehouse.controller;

import com.restaurant.dinehouse.model.*;
import com.restaurant.dinehouse.service.TranService;
import com.restaurant.dinehouse.util.SystemConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TransactionController {

    private final TranService tranService;

    @PostMapping("/expenses")
    public ResponseEntity<Response<List<Transaction>>> saveExpenseRecords(@RequestBody List<TransactionRequest> transactions) {
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS, tranService.saveTransactions(transactions)));
    }

    @PostMapping("/web-ui/tran-record")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseEntity<Response<List<Transaction>>> savePaymentInfo(@RequestBody TransactionRequest transaction) {
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS,
                tranService.saveTransactions(Arrays.asList(transaction))));
    }

    @DeleteMapping("/web-ui/tran-record/{id}")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseEntity<Response<Long>> deletePaymentInfo(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS,
                tranService.deleteTransactionById(id)));
    }

    @PostMapping("/order-payment")
    public ResponseEntity<Response<Transaction>> payForOrder(@RequestBody TransactionRequest transaction) {
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS, tranService.saveOrderPaymentInfo(transaction)));
    }

    @PostMapping("/web-ui/order-payment")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseEntity<Response<Transaction>> orderPayment(@RequestBody TransactionRequest transaction) {
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS, tranService.saveOrderPaymentInfo(transaction)));
    }

    @GetMapping("/transactions")
    public ResponseEntity<Response<List<Transaction>>> getTransactions() {
        return ResponseEntity.ok(new Response<>(SystemConstants.SUCCESS, tranService.getCurrentDateTransactions()));
    }

    @GetMapping("/web-ui/transactions/dr")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseEntity<List<Transaction>> getDebitRecords() {
        return ResponseEntity.ok(tranService.getDebitRecords());
    }

    @GetMapping("/daily-report/trans")
    public ResponseEntity<List<DailyAggregateTrans>> getDailyReportsOnTrans() {
        return ResponseEntity.ok(tranService.getDailyReportOnTrans());
    }
}
