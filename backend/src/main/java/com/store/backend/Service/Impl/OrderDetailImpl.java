package com.store.backend.Service.Impl;

import com.store.backend.Entity.OrderDetail;
import com.store.backend.Repository.OrderDetailRepository;
import com.store.backend.Service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository repository;

    @Override
    public OrderDetail saveOrderDetail(OrderDetail orderDetail) {
        return repository.save(orderDetail);
    }
}
