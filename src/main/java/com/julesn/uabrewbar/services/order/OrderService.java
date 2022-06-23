package com.julesn.uabrewbar.services.order;

import com.julesn.uabrewbar.domain.Order;
import com.julesn.uabrewbar.domain.Status;

import java.util.List;

public interface OrderService {
    boolean updateStatus(Order order);

    Order getOrderByNumber(String bar, Long number);

    List<Order> getOrdersByUser(String bar, String user);

    List<Order> getOrdersByStatus(String bar, Status status);

    boolean createOrder(Order order) throws Exception;

    boolean transferMoney(Long number, String bar, float transaction);
}
