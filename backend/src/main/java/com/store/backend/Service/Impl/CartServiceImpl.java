package com.store.backend.Service.Impl;

import com.store.backend.Entity.Cart;
import com.store.backend.Entity.User;
import com.store.backend.Repository.CartRepository;
import com.store.backend.Service.CartService;
import com.store.backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository repo;

    @Autowired
    private UserService userService;

    @Override
    public Cart getByUserId(Long id) {
        return repo.getByUser_Id(id);
    }

    public Cart findByUser(User user){
        return repo.findByUser(user);
    }

    @Override
    public Cart save(Cart cart) {
        return repo.save(cart);
    }
}
