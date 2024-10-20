package com.store.backend.Service;

import com.store.backend.Entity.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {
    List<Order> getAllOrders();

    List<Order> getByUserId(Long id);


    Order addOrder(Order order);

    void deleteOrder(Order order);

    List<Map<String, ?>> getReportOrderByMonth(String year);

    boolean finish(Long id);

    boolean deleteById(Long id);

    Order getByOrderById(long id);
}
