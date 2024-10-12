package com.DDN.login.repository;

import com.DDN.login.model.Order;
import com.DDN.login.model.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrder(Order order);

    @Query(value="SELECT sum(price*quantity) from" +
            " order_items " +
            "where created_date <  (date_trunc('month', now())  + '1 MONTH')" +
            " AND created_date > (date_trunc('month', now()))"
            , nativeQuery = true
            )
    Integer getResult();

    @Query(value="SELECT * from" +
            " order_items " +
            "ORDER BY created_date DESC " +
            "LIMIT 5"
            , nativeQuery = true
    )
    List<OrderItem> getOrderLatest();

}
