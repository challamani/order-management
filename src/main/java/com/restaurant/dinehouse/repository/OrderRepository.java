package com.restaurant.dinehouse.repository;

import com.restaurant.dinehouse.model.DailyAggregateOrders;
import com.restaurant.dinehouse.model.Order;
import com.restaurant.dinehouse.model.OrderInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order,Long> {

    List<Order> findByUserId(String userId);

    @Query(value = "select * from order_master where created_on >= CURDATE() and created_on < CURDATE() + INTERVAL 1 DAY order by status", nativeQuery = true)
    List<Order> findCurrentDateOrders();

    @Query(value = "select m.id,m.user_id as userId, m.status, m.served_by as servedBy, m.price, m.payable_amount as payableAmount, m.type, m.address,t.payment_method as paymentMethod, t.description as description " +
            "   from order_master m left join transactions t on t.order_id = m.id " +
            "   where m.created_on >= CURDATE() and m.created_on < CURDATE() + INTERVAL 1 DAY " +
            "   order by m.id desc", nativeQuery = true)
    List<OrderInfo> findCurrentDateOrderInfo();

    @Query(value = "select m.user_id as userId, m.served_by as servedBy, count(*) as quantity,sum(m.payable_amount) as amount, " +
            "   m.type as type, m.status as status, t.payment_method as paymentMethod  " +
            "   from order_master m left join transactions t on m.id=t.order_id " +
            "   where m.created_on >= CURDATE() and m.created_on < CURDATE() + INTERVAL 1 DAY " +
            "   group by m.user_id, m.served_by, m.status, m.type, t.payment_method", nativeQuery = true)
    List<DailyAggregateOrders> findByGroupByOrders();

    @Query(value = "select sum(m.payable_amount) as amount, t.payment_method as paymentMethod, t.type as type  " +
            "   from order_master m left join transactions t on t.order_id = m.id " +
            "   where m.created_on >= CURDATE() and m.created_on < CURDATE() + INTERVAL 1 DAY " +
            "   group by t.type, t.payment_method", nativeQuery = true)
    List<DailyAggregateOrders> findOrdersByGroup();
}
