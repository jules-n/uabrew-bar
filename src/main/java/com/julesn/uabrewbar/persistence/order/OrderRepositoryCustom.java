package com.julesn.uabrewbar.persistence.order;

import com.julesn.uabrewbar.domain.Order;

import java.util.List;
import java.util.Map;

public interface OrderRepositoryCustom {
    boolean updateByCriteria(Map<String, Object> criteria, Map<String, Object> data);
    Order getByCriteria(Map<String, Object> criteria);
    List<Order> getListByCriteria(Map<String, Object> criteria);
}
