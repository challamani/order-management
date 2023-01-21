package com.restaurant.dinehouse.repository;

import com.restaurant.dinehouse.model.DailyAggregateOrders;
import com.restaurant.dinehouse.model.DailyAggregateTrans;
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

    @Query(value = "select t.user_id as userId, sum(t.amount) as amount, " +
            "   t.tran_group as tranGroup, t.payment_method as paymentMethod, t.type as type " +
            "   from transactions t " +
            "   where t.created_on >= CURDATE() and t.created_on < CURDATE() + INTERVAL 1 DAY " +
            "   group by t.user_id, t.payment_method, t.type, t.tran_group ", nativeQuery = true)
    List<DailyAggregateTrans> findByGroupByTransactions();
}
