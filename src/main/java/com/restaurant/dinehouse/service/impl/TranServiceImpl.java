package com.restaurant.dinehouse.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.restaurant.dinehouse.model.*;
import com.restaurant.dinehouse.repository.OrderRepository;
import com.restaurant.dinehouse.repository.TransactionRepository;
import com.restaurant.dinehouse.service.TranService;
import com.restaurant.dinehouse.util.SystemConstants;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TranServiceImpl implements TranService {

    private final OrderRepository orderRepository;
    private final TransactionRepository transactionRepository;


    @Override
    @Transactional
    public Transaction saveOrderPaymentInfo(TransactionRequest transaction) {

        try {
            log.info("order payment request {}", Json.mapper().writeValueAsString(transaction));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Transaction transactionRequest = new Transaction();
        StringBuilder desc = new StringBuilder(StringUtils.defaultIfBlank(transaction.getDescription(), ""));
        Optional<Order> dbOrderOptional = orderRepository.findById(Long.parseLong(transaction.getOrderId()));
        if (dbOrderOptional.isPresent()) {
            Order dbOrder = dbOrderOptional.get();
            if (transaction.getAmount() < dbOrder.getPayableAmount()) {
                desc.append(String.format(" actual amount to pay %s but paid %s",
                        dbOrder.getPayableAmount().toString(), transaction.getAmount()));
            }
            transactionRequest.setDescription(desc.toString());
            transactionRequest.setType(SystemConstants.TranType.Cr);
            transactionRequest.setAmount(transaction.getAmount());
            transactionRequest.setPaymentMethod(transaction.getMethod());

            if (StringUtils.isNoneBlank(transaction.getOrderId())) {
                transactionRequest.setTranGroup(SystemConstants.TranGroup.Order);
            } else {
                transactionRequest.setTranGroup(transaction.getType());
            }
            transactionRequest.setUserId(transaction.getUserId());
            transactionRequest.setOrderId(transaction.getOrderId());

            Transaction tran = transactionRepository.save(transactionRequest);
            dbOrder.setStatus(SystemConstants.OrderStatus.PAID);
            orderRepository.save(dbOrder);
            return tran;
        }
        throw new RuntimeException("The given order not existed in system order# " + transaction.getOrderId());
    }

    @Override
    public List<Transaction> saveTransactions(List<TransactionRequest> transactions) {

        List<Transaction> transactionList = new ArrayList<>();
        transactions.stream().forEach(tran -> {

            Transaction transaction = new Transaction();
            transaction.setType((tran.getType() == SystemConstants.TranGroup.Order) ? SystemConstants.TranType.Cr : SystemConstants.TranType.Dr);
            transaction.setUserId(tran.getUserId());
            transaction.setPaymentMethod(tran.getMethod());
            transaction.setTranGroup(tran.getType());
            transaction.setDescription(tran.getDescription());
            transaction.setOrderId(tran.getOrderId());
            transaction.setAmount(tran.getAmount());
            if (Objects.nonNull(tran.getId()) && tran.getId() >0) {
                transaction.setId(tran.getId());
            }
            transactionList.add(transaction);
        });

        Iterable<Transaction> transactionIterator = transactionRepository.saveAll(transactionList);
        List<Transaction> transactions1 = new ArrayList<>();
        transactionIterator.forEach(transactions1::add);
        return transactions1;
    }

    @Override
    public List<Transaction> getCurrentDateTransactions() {
        return transactionRepository.findCurrentDateTransactions();
    }

    @Override
    public List<Transaction> getDebitRecords() {
        return transactionRepository.findCurrentDateTransactions().stream()
                .filter(tran -> tran.getType() == SystemConstants.TranType.Dr)
                .collect(Collectors.toList());
    }

    @Override
    public Long deleteTransactionById(Long id) {
        transactionRepository.deleteById(id);
        return id;
    }

    @Override
    public List<DailyAggregateTrans> getDailyReportOnTrans() {
        return transactionRepository.findByGroupByTransactions();
    }

    @Override
    public List<BalanceSheetResponse> getBalanceSheet() {

        List<DailyAggregateOrders> dailyAggregateOrders = orderRepository.findOrdersByGroup();
        List<DailyAggregateTrans> dailyAggregateTrans = transactionRepository.findTransactionsByPaymentMethod();

        List<BalanceSheetResponse> balanceSheetResponses = new ArrayList<>();

        dailyAggregateOrders.stream().forEach(aggOrder -> {
            BalanceSheetResponse balanceSheetResponse = new BalanceSheetResponse();
            balanceSheetResponse.setActivity("Order Payment");
            balanceSheetResponse.setExpected(aggOrder.getAmount().toString());
            balanceSheetResponse.setPaymentMethod(aggOrder.getPaymentMethod());
            balanceSheetResponse.setType(SystemConstants.TranType.Cr);

            Optional<DailyAggregateTrans> optionalAggregateTrans = dailyAggregateTrans.stream().filter(aggTran ->
                    aggTran.getPaymentMethod() == aggOrder.getPaymentMethod() &&
                            aggTran.getType() == SystemConstants.TranType.Cr).findFirst();

            if (optionalAggregateTrans.isPresent()) {
                balanceSheetResponse.setActual(optionalAggregateTrans.get().getAmount().toString());
            } else {
                balanceSheetResponse.setActual("NA");
            }
            balanceSheetResponses.add(balanceSheetResponse);
        });

        dailyAggregateTrans.stream().filter(aggTran -> aggTran.getType() == SystemConstants.TranType.Dr)
                .forEach(aggTrans -> {
                    BalanceSheetResponse balanceSheetResponse = new BalanceSheetResponse();
                    balanceSheetResponse.setActivity("Expenses");
                    balanceSheetResponse.setExpected("NA");
                    balanceSheetResponse.setPaymentMethod(aggTrans.getPaymentMethod());
                    balanceSheetResponse.setType(SystemConstants.TranType.Dr);
                    balanceSheetResponse.setActual(aggTrans.getAmount().toString());
                });
        return balanceSheetResponses;
    }
}
