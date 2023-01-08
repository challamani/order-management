package com.restaurant.dinehouse.service.impl;

import com.restaurant.dinehouse.model.Order;
import com.restaurant.dinehouse.model.Transaction;
import com.restaurant.dinehouse.repository.OrderRepository;
import com.restaurant.dinehouse.repository.TransactionRepository;
import com.restaurant.dinehouse.service.TranService;
import com.restaurant.dinehouse.util.SystemConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TranServiceImpl implements TranService {

    private final OrderRepository orderRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public Transaction saveOrderPaymentInfo(Transaction transaction) {

        StringBuilder desc = new StringBuilder(StringUtils.defaultIfBlank(transaction.getDescription(), ""));
        Optional<Order> dbOrderOptional = orderRepository.findById(Long.parseLong(transaction.getOrderId()));
        if (dbOrderOptional.isPresent()) {
            Order dbOrder = dbOrderOptional.get();
            if (transaction.getAmount() < dbOrder.getPayableAmount()) {
                desc.append(String.format("actual amount to pay %s but paid %s",
                        dbOrder.getPayableAmount().toString(), transaction.getAmount()));
            }
            transaction.setDescription(desc.toString());
            transaction.setType(SystemConstants.TranType.Cr);
            Transaction tran = transactionRepository.save(transaction);

            dbOrder.setStatus(SystemConstants.OrderStatus.PAID);
            orderRepository.save(dbOrder);
            return tran;
        }
        throw new RuntimeException("The given order not existed in system order# " +transaction.getId());
    }

    @Override
    public List<Transaction> saveTransactions(List<Transaction> transactions) {
        Iterable<Transaction> transactionIterator = transactionRepository.saveAll(transactions);
        List<Transaction> transactions1 = new ArrayList<>();
        transactionIterator.forEach(transactions1::add);
        return transactions1;
    }
}
