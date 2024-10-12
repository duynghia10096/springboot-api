package com.DDN.login.repository;

import com.DDN.login.dto.OrderDto;
import com.DDN.login.dto.RevenueDTO;
import com.DDN.login.model.Order;
import com.DDN.login.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<RevenueDTO> getFilterRevenue6LastMonth();
    List<Order> findByUser(User user);


    @Query(value = "SELECT sum(total_price + tax) from" +
            " orders " +
            "where to_char(created_at, 'DD/MM/YYYY') = to_char(now(), 'DD/MM/YYYY')"
            , nativeQuery = true)
    Integer getTotalPriceDaily();



}
