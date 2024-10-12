package com.DDN.login.controller;

import com.DDN.login.dto.OrderItemDTO;
import com.DDN.login.dto.RevenueDTO;
import com.DDN.login.model.Order;
import com.DDN.login.model.OrderItem;
import com.DDN.login.repository.OrderItemRepository;
import com.DDN.login.repository.OrderRepository;
import com.DDN.login.repository.UserReposity;
import com.DDN.login.repository.dao.info.ProductInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/calculate")
public class CalculateController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private UserReposity userReposity;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @GetMapping("/getCount")
    public ResponseEntity<Map<String, Object>> getCountAll() {
        Map<String, Object> response = new HashMap<>();
        Long countProduct = productInfoRepository.count();
        Long countOrder = orderRepository.count();
        Long countUser = userReposity.count();
        response.put("countProduct", countProduct);
        response.put("countOrder", countOrder);
        response.put("countUser", countUser);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getDailyRevenue")
    public ResponseEntity<Map<String, Object>> getDailyIncome() {
        Map<String, Object> response = new HashMap<>();
        Integer totalIncome = orderRepository.getTotalPriceDaily();
        if (totalIncome == null) {
            totalIncome = 0;
        }
        response.put("totalIncomeDaily", totalIncome);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getMonthRevenue")
    public ResponseEntity<Map<String,Object>> getRevenue() {
        Map<String, Object> response = new HashMap<>();
        Integer monthRevenue = orderItemRepository.getResult();
        if(monthRevenue == null) {
            monthRevenue = 0;
        }
        response.put("totalIncomeMonth", monthRevenue);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getLatestOrder")
    public ResponseEntity<List<OrderItemDTO>> getLatestOrder() {
        List<OrderItem> orderItem = orderItemRepository.getOrderLatest();
        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

        for(int i = 0; i < orderItem.size(); i++) {
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setOrderId(orderItem.get(i).getId());
            orderItemDTO.setAmount(orderItem.get(i).getQuantity());
            orderItemDTO.setStatus(orderItem.get(i).getOrder().getStatus());
            orderItemDTO.setCustomerName(orderItem.get(i).getOrder().getUsername());
            orderItemDTO.setProductName(orderItem.get(i).getName());
            orderItemDTO.setImageURL(orderItem.get(i).getImageUrl());
            orderItemDTO.setCreatedDate(orderItem.get(i).getCreatedDate());
            orderItemDTO.setPrice(orderItem.get(i).getPrice());
            orderItemDTOList.add(orderItemDTO);
        }

        return new ResponseEntity<>(orderItemDTOList, HttpStatus.OK);
    }

    @GetMapping("/getLast6Month")
    public ResponseEntity<List<RevenueDTO>> getLast6Month() {
        List<RevenueDTO> response = new ArrayList<>();
        response = orderRepository.getFilterRevenue6LastMonth();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
