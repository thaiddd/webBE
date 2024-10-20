package com.store.backend.Service.Impl;

import com.store.backend.Entity.Order;
import com.store.backend.Repository.OrderRepository;
import com.store.backend.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getByUserId(Long id) {
        return orderRepository.getByUser_IdAndState(id, "Shipping");
    }

    @Override
    public Order addOrder(Order order) {
        order.setIsDelete(0L);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Order order) {
        orderRepository.deleteById(order.getId());
    }

    @Override
    public List<Map<String, ?>> getReportOrderByMonth(String year) {
        return orderRepository.getReportOrderCount(year);
    }

    @Override
    public boolean finish(Long id) {
        Order order = orderRepository.findById(id).get();
        order.setState("Done");
        order.setDateOfReceipt(Date.valueOf(LocalDate.now()));
        orderRepository.save(order);
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        Order order = orderRepository.findById(id).get();
        order.setIsDelete(1L);
        orderRepository.save(order);
        return true;
    }

    @Override
    public Order getByOrderById(long id) {
        return orderRepository.getOrderById(id);
    }
}
