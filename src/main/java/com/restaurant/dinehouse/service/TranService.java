package com.restaurant.dinehouse.service;

import com.restaurant.dinehouse.model.Transaction;

import java.util.List;

public interface TranService {

    Transaction saveOrderPaymentInfo(Transaction transaction);
    List<Transaction> saveTransactions(List<Transaction> transactions);
}
