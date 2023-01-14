package com.restaurant.dinehouse.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.restaurant.dinehouse.model.Order;
import com.restaurant.dinehouse.model.Transaction;
import com.restaurant.dinehouse.model.TransactionRequest;
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
}
