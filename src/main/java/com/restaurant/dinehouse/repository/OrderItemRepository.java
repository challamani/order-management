package com.restaurant.dinehouse.repository;

import com.restaurant.dinehouse.model.DailyAggregateItems;
import com.restaurant.dinehouse.model.OrderItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderItemRepository extends CrudRepository<OrderItem,Long> {
    List<OrderItem> findByOrderId(Long orderId);
    void deleteByOrderId(Long orderId);
    @Query(value = "select item_name as name, sum(item.quantity) as quantity,sum(item.price) as amount, m.type as type, m.status as status  " +
            "   from order_item as item join order_master m on m.id=item.order_id " +
            "   where item.created_on >= CURDATE() and item.created_on < CURDATE() + INTERVAL 1 DAY " +
            "   group by item.item_name, m.status, m.type", nativeQuery = true)
    List<DailyAggregateItems> findByGroupByItems();
}
