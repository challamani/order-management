package com.restaurant.dinehouse.service;

import com.restaurant.dinehouse.model.Transaction;
import com.restaurant.dinehouse.model.TransactionRequest;

import java.util.List;

public interface TranService {

    Transaction saveOrderPaymentInfo(TransactionRequest transaction);
    List<Transaction> saveTransactions(List<TransactionRequest> transactions);
    List<Transaction> getCurrentDateTransactions();
    List<Transaction> getDebitRecords();
}
