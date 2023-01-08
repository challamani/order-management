package com.restaurant.dinehouse.repository;

import com.restaurant.dinehouse.model.Transaction;
import com.restaurant.dinehouse.util.SystemConstants;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction,Long> {

    List<Transaction> findByUserId(String userId);

    List<Transaction> findByUserIdAndTypeAndCreatedOnGreaterThan(String userId, SystemConstants.TranType tranType, String createdOn);
}