package com.restaurant.dinehouse.repository;

import com.restaurant.dinehouse.model.Transaction;
import com.restaurant.dinehouse.util.SystemConstants;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction,Long> {

    List<Transaction> findByUserId(String userId);

    List<Transaction> findByUserIdAndTypeAndCreatedOnGreaterThan(String userId, SystemConstants.TranType tranType, String createdOn);

    @Query(value = "select * from transactions where created_on >= CURDATE() and created_on < CURDATE() + INTERVAL 1 DAY", nativeQuery = true)
    List<Transaction> findCurrentDateTransactions();

    @Query(value = "select * from transactions where type='Dr' and (created_on >= CURDATE() and created_on < CURDATE() + INTERVAL 1 DAY)", nativeQuery = true)
    List<Transaction> findCurrentDateDebits();
}
