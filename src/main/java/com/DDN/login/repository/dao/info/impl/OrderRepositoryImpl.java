package com.DDN.login.repository.dao.info.impl;

import com.DDN.login.dto.RevenueDTO;
import com.DDN.login.utils.ListResultTransFormer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;


    public List<RevenueDTO> getFilterRevenue6LastMonth() {
        ListResultTransFormer listResultTransFormer = new ListResultTransFormer();
        List<RevenueDTO> revenueDTOList = listResultTransFormer.getRevenue6LastMonths(
                "SELECT TO_CHAR(created_at, 'Month'), sum(total_price) from" +
                        " orders " +
                        "GROUP BY TO_CHAR(created_at, 'Month') " +
                        "ORDER BY TO_DATE(CONCAT('0001 ', to_char(created_at,'Month'), ' 01'),  'Y Mon')"
                , entityManager
        );
        return revenueDTOList;
    }
}
