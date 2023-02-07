package com.restaurant.dinehouse.service;

import com.restaurant.dinehouse.model.*;

import java.util.List;

public interface TranService {

    Transaction saveOrderPaymentInfo(TransactionRequest transaction);
    List<Transaction> saveTransactions(List<TransactionRequest> transactions);
    List<Transaction> getCurrentDateTransactions();
    List<Transaction> getDebitRecords();
    Long deleteTransactionById(Long id);
    List<DailyAggregateTrans> getDailyReportOnTrans();
    List<BalanceSheetResponse> getBalanceSheet();
}
